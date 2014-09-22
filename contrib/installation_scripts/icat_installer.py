#!/usr/env python
from subprocess import call, PIPE, Popen
import os
from string import upper
import socket
import sys
import ConfigParser

CFG = "icat_config.cfg"
HOSTNAME = socket.gethostname()

#### TODO
# Better SQL configuration (if one SQL command fails, everything fails)


def cfg_reader(cfgname, services):
    """Returns: configuration dictionary"""
    cfg_d = {}
    config = ConfigParser.ConfigParser()
    config.optionxform = str
    config.read(cfgname)
    services.append('version')
    services.append('setup')
    services.append('general')
    for service in config.sections():
        cfg_d[service] = dict(config.items(service))
    return cfg_d


def run_sql(sql, user='root'):
    print "[INFO][sql] Executing SQL query as " + user
    if sql == "":
        return
    f = open("my.sql", "w")
    f.write(sql)
    f.close()
    # TODO can I avoid using shell=True?
    ret = call(['mysql -u ' + user + ' -p <  my.sql'], stdin=PIPE, shell=True)
    if ret != 0:
        print "[WARN][sql] SQL command failed!"


def unzip(services):
    # unzipping
    for f in os.listdir('.'):
        if f[-4:] != ".zip":
            continue
        p = call(['unzip', '-u', f], stdout=PIPE, stderr=PIPE, )


def configure_topcat(cfg):
    """Configure TopCAT"""
    # TopCAT source directory has a name which does not conform
    # to the rest...
    #call(['mv', 'topcat.'+cfg['version']['topcat'], "topcat"])
    prop_fname = configure_generic(cfg, 'topcat')
    f = open(prop_fname, 'a')  # append?
    f.write("topcatWar=TopCAT-" + cfg['version']['topcat'] + ".war\n")
    f.write("topcatAdminWar=TopCATAdmin-" + cfg['version']['topcat'] + ".war\n")
    f.close()
    prop_fname = "topcat/topcat.properties"
    f = open(prop_fname, 'w')
    for k, v in cfg['topcat'].items():
        f.write(k + "=" + v + "\n")
    f.close()
    call(["chmod", "600", prop_fname])

    # create certificate
    cmd = """export GF_CONFIG=""" + cfg['setup']['glassfish'] + """/glassfish/domains/domain1/config;"""
    cmd += """export GF_CERT=$GF_CONFIG/my_glassfish.cert;"""
    cmd += """openssl s_client -showcerts -connect """ + HOSTNAME + """:8181 </dev/null | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > $GF_CERT;"""
    cmd += cfg['general']['java_home'] + """/bin/keytool -import -noprompt -alias ALIAS -file $GF_CERT -keystore $GF_CONFIG/cacerts.jks --storepass changeit """
    cert_f = open("cert.sh", 'w')
    cert_f.write(cmd)
    cert_f.close()
    call(['bash', 'cert.sh'])


def configure_icat(cfg, services):
    """Configure ICAT"""
    configure_generic(cfg, 'icat')
    prop_fname = "icat/icat.properties"
    prop_f = open(prop_fname, 'w')
    # Desired authentication plugin mnemonics
    authn_list = "authn.list="
    jndi_list = ""
    for s in services:
        if s.find('authn') == -1:
            continue
        authn_list += s.split("_")[-1] + " "
        m = s.split('.')[-1].split("_")[-1]
        jndi_list += "authn." + m + ".jndi java:global/" + s + "-" + cfg['version'][s] + "/" + upper(m) + "_Authenticator"
        # JNDI for each plugin
        #icat.authn.db.jndi=java:global/authn_db-1.1.1/DB_Authenticator
        #icat.authn.ldap.jndi=java:global/authn_ldap-1.1.0/LDAP_Authenticator
    for k, v in cfg['icat'].items():
        prop_f.write(k + "=" + v + "\n")
    prop_f.write(authn_list + "\n")
    prop_f.write(jndi_list + "\n")
    ### create bin directory (required, sigh...)
    p = Popen(['mkdir', os.environ["HOME"] + '/bin'])


def configure_authn(cfg, authn):
    """Configure Authn"""
    # TODO add mysql configuration for db
    configure_generic(cfg, authn)


def configure_generic(cfg, service):
    """Generic, simple configurator. To be used e.g. with icat-setup, ICE"""
    prop_fname = service + "/" + service + "-setup.properties"
    if os.path.isfile(prop_fname):
        print "[WARN][" + service + "] Configuration file exist, removing it"
        os.remove(prop_fname)
    prop_f = open(prop_fname, 'w')
    for service in ['setup', service + "_setup"]:
        if service not in cfg.keys():
            continue
        for k, v in cfg[service].items():
            prop_f.write(k + "=" + v + "\n")
    prop_f.close()
    call(["chmod", "600", prop_fname])
    return prop_fname


def get_user_pwd(prop):
    d = dict([i.split("=") for i in prop.split(':')])
    return d


def configure_sql(cfg, services):
    """Configure ICAT-related SQL DB"""
    print "[INFO][sql] Configuring SQL"
    sql = ""
    if 'icat' in services:
        t = cfg['icat_setup']['dbProperties'].split(':')
        d = dict([i.split("=") for i in t])
        sql += "create database " + d['databaseName'] + ";\n"
        sql += "grant all privileges on " + d['databaseName'] + ".* to " + d['user'] + "@localhost identified by '" + d['password'] + "';\n"

    # if you want to use DB-based auth, do also
    if 'authn_db' in services:
        t = cfg['authn_db_setup']['authn_dbProperties'].split(':')
        d = dict([i.split("=") for i in t])
        sql += "create database authn_db;\n"
        sql += "grant all privileges on authn_db.* to " + d['user'] + "@localhost identified by '" + d['password'] + "';\n"
    if 'topcat' in services:
        d = get_user_pwd(cfg['topcat_setup']['dbProperties'])
        sql += "create database topcat;\n"
        sql += "grant all privileges on " + d['databaseName'] + ".* to " + d['user'] + "@localhost identified by '" + d['password'] + "';\n"
    run_sql(sql)


def install_generic(service):
    """Generic, simple installer. To be used e.g. with icat-setup, ICE"""
    old_pwd = os.getcwd()
    os.chdir(service)
    call(["./setup", "--verbose", "configure"])
    if service == 'icat':
        prop_fname = "icat/icat.properties"
        Popen(['mv', prop_fname + ".example", prop_fname + ".example.orig"])
    call(["./setup", "--verbose", "install"])
    os.chdir(old_pwd)


def install_topcat(service):
    """Unsurprisingly, TopCAT setup files have different names..."""
    old_pwd = os.getcwd()
    os.chdir(service)
    call(["python", "setup", "--verbose", "configure"])
    call(["python", "setup", "--verbose", "install"])
    os.chdir(old_pwd)


def get_tars(services, cfg):
    """Download ICAT (and related) tars"""
    # The case-by-case code is needed as there is no real convention in file naming scheme
    print "[INFO][get_tars] Downloading archives from the Web"
    vers = cfg['version']
    url = 'http://www.icatproject.org/mvn/repo/org/icatproject'
    if 'icat' in services:
        p = call(["wget", url + "/icat.ear/" + vers['icat'] + "/icat.ear-" + vers['icat'] + "-distro.zip"], stdout=PIPE, stderr=PIPE,)
    if 'authn_ldap' in services:
        p = call(['wget', url + '/authn_ldap/' + vers['authn_ldap'] + '/authn_ldap-' + vers['authn_ldap'] + '-distro.zip', ], stdout=PIPE, stderr=PIPE, )
    if 'authn_db' in services:
        p = call(['wget', url + '/authn_db/' + vers['authn_db'] + '/authn_db-' + vers['authn_db'] + '-distro.zip', ], stdout=PIPE, stderr=PIPE, )
    if 'icat-setup' in services:
        p = call(['wget', url + '/icat-setup/' + vers['icat-setup'] + '/icat-setup-' + vers['icat-setup'] + '-distro.zip', ], stdout=PIPE, stderr=PIPE, )
    if 'ice' in services:
        p = call(['wget', url + '/ice/' + vers['ice'] + '/ice-' + vers['ice'] + '-distro.zip', ], stdout=PIPE, stderr=PIPE, )
    if 'topcat' in services:
        p = call(['wget', 'http://www.icatproject.org/mvn/repo/org/icatproject/TopCAT/' + vers['topcat'] + '/TopCAT-' + vers['topcat'] + '-distro.zip', ], stdout=PIPE, stderr=PIPE, )
    print "[INFO][get_tars] Done"


def test_icat(cfg, authn):
    """Testing ICAT installation"""
    if authn == "authn_db":
        print "[INFO][test_icat] Test using " + authn + ""
        uname = cfg[authn]['users'].split(":")[0].split("=")[0]
        pwd = cfg[authn]['users'].split(":")[0].split("=")[1]
        P = call(['icat/testicat', 'https://' + HOSTNAME + ':8181',
                  'db', 'username', uname, 'password', pwd],)
        if P != 0:
            print "[ERROR][test_icat] Test using " + authn + " failed."
            sys.exit(-1)
        print "[INFO][test_icat] Test using " + authn + " succeeded"


def main():
    # CLI arguments
    import argparse
    parser = argparse.ArgumentParser()
    parser.add_argument("-n", "--nodownload", help="""Do not download files.
    general.tmp_dir should then point to where source directories are stored""",
                        action="store_true", default=False)
    parser.add_argument("--services", help="""Services to be installed (comma-separated list).
    Possible values: authn_db, authn_ldap, icat, icat-setup, ice, topcat""",
                        action="store", default="authn_db,icat,icat-setup,ice,topcat")
    args = parser.parse_args()

    # basic configuration
    services = [s for s in args.services.split(",")]
    cfg = cfg_reader(CFG, services)

    # create tmp dir
    if not os.path.isdir(cfg['general']['tmp_dir']):
        os.mkdir(cfg['general']['tmp_dir'])
    os.chdir(cfg['general']['tmp_dir'])

    # Download
    if not args.nodownload:
        get_tars(services, cfg)
        unzip(services)

    # configure MYSQL
    configure_sql(cfg, services)

    # authn_db installation
    if "authn_db" in services:
        configure_authn(cfg, 'authn_db')
        install_generic('authn_db')
        sql = "use authn_db;\n"
        for up in cfg['authn_db']['users'].split(':'):
            sql += "INSERT INTO PASSWD (USERNAME, ENCODEDPASSWORD) VALUES ('" + up.split('=')[0] + "', '" + up.split('=')[1] + "');\n"
        run_sql(sql)

    # icat installation
    if 'icat' in services:
        configure_icat(cfg, services)
        install_generic('icat')
        for auth in services:
            if auth.find("auth") == -1:
                continue
            test_icat(cfg, auth)

    # icat-setup installation
    if 'icat-setup' in services:
        if not os.path.isdir(os.environ["HOME"] + "/java"):
            print "[INFO][icat-setup] Creating directory ~/java"
            os.mkdir(os.environ["HOME"] + "/java")
        configure_generic(cfg, 'icat-setup')
        install_generic('icat-setup')
        print "[INFO][icat-setup] Installation DONE"

    # ice installation
    if 'ice' in services:
        print "[INFO][ice] Installing"
        p = call(['cp', 'ice/ice.properties',
                  cfg['setup']['glassfish'] + '/glassfish/domains/domain1/config'])
        p = call([cfg['setup']['glassfish'] + '/bin/asadmin',
                  'deploy', '--contextroot', 'ice', 'ice/ice-' + cfg['version']['ice'] + '.war'])
        print "[INFO][ice] Installation DONE"

    # topcat installation
    if 'topcat' in services:
        configure_topcat(cfg)
        install_topcat('topcat')

    print "[INFO][main] DONE"


if __name__ == '__main__':
    main()
