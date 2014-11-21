#!/usr/bin/env python

"""
Installer script for the ICAT Metadata Catalogue
"""

from subprocess import call, PIPE, Popen, check_call, check_output, CalledProcessError
import os
from string import upper
import socket
import sys
import ConfigParser
try:
    from mysql import connector as mc
except:
    print """
Module mysql.connector missing! Please install it in your python environment using e.g.:
    + pip install mysql-connector-python --allow-external mysql-connector-python
    + yum install mysql-connector-python (for a RedHat based distribution)
    + conda install mysql-python (if you are using the Anaconda Python distribution)
    """
    sys.exit(-1)

import logging
logger = logging.getLogger(__name__)
logging.basicConfig(level=logging.INFO, format='[%(levelname)s][%(funcName)s] %(message)s')

CFG = "icat_config.cfg"
HOSTNAME = socket.gethostname()


def cfg_reader(cfgname, services):
    """
    Reads configuration file (in the form of an .ini file), and returns a configuration dictionary

    :param cfgname: Name of the INI-style configuration file
    :param services: List of services to be configured
    :return: Dictionary of configurations
    """
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


def run_sql(cfg, sql):
    """
    Run a MySQL statement

    :param cfg: the configuration dict
    :param sql: a string with the MySQL statement
    """

    logger.info("Executing SQL query as sql_user=" + cfg["general"]["sql_user"])
    if sql == "":
        logger.warning("Cowardly refusing to execute empty SQL command")
        return
    # connecting to MySQL db
    try:
        db = mc.connect(host=cfg["general"]["sql_host"], user=cfg["general"]["sql_user"], passwd=cfg["general"]["sql_password"])
        cur = db.cursor()
    except:
        logger.error("Cannot connect to MySQL, error: %s" % sys.exc_info()[1])
        sys.exit(-1)
    # checking if DB exists before creating it
    for l in sql.split(";"):
        if l.lower().find("create database") != -1:
            db_name = l.strip(";").split()[-1]
            out = cur.execute("SHOW DATABASES LIKE '%s';" % db_name, multi=True)
            nrows = len(out.next().fetchall())
            if nrows != 0:
                logger.warning("Cowardly refusing to create DB %s, as it already exist" % db_name)
                cur.close()
                db.close()
                return

    logger.debug("Running SQL statement: \n--- BEGIN SQL\n %s\n--- END SQL\n" % sql.strip("\n"))
    try:
        out = cur.execute(sql, multi=True)
        for i in out:
            nrows = i.rowcount
            warns = i.fetchwarnings()
        db.commit()
    except:
        logger.error("Cannot execute MySQL query: \n--- BEGIN SQL\n %s\n--- END SQL\n" % sql.strip("\n"))
        logger.error("MySQL error: %s" % sys.exc_info()[1])
        cur.close()
        db.close()
        sys.exit(-1)
    cur.close()
    db.close()


def unzip():
    """
    A simple unzipper function
    """
    logger.debug("Unzipping...")
    for f in os.listdir('.'):
        if f[-4:] != ".zip":
            continue
        try:
            check_call(['unzip', '-u', f], stdout=PIPE, stderr=PIPE, )
        except:
            logger.error("Error in unzipping %s" % f)
            logger.error("Reported error: %s" % sys.exc_info())


def configure_topcat(cfg):
    """
    Configure TopCAT

    :param cfg: the configuration dict
    """
    logger.debug("Called")
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


def configure_icat(cfg, services):
    """
    Configure ICAT

    :param cfg: the configuration dict
    :param services: the services list (used to get the list of authentication plugins)
    """
    logger.info("Starting configuration")
    configure_generic(cfg, 'icat')
    prop_fname = "icat/icat.properties"
    prop_f = open(prop_fname, 'w')
    # Desired authentication plugin mnemonics
    authn_list = "authn.list="
    jndi_list = ""
    authn_entries = [k for k in cfg.keys() if k.find("authn") != -1 and k.find("setup") == -1]
    logger.info("Authentication plugins in configuration file: %s" % str(authn_entries))
    for s in authn_entries:
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
    """
    Configure Authentication plugins

    :param cfg: the configuration dict
    :param authn: name of the authn service to be configured
    """
    configure_generic(cfg, authn)


def configure_generic(cfg, service):
    """
    Generic, simple configurator.

    :param cfg: the configuration dict
    :param service: name of the service to be configured
    :return: properties filename
    """
    logger.info("Configuring %s" % service)
    prop_fname = service + "/" + service + "-setup.properties"
    if os.path.isfile(prop_fname):
        logger.warning("[" + service + "] Configuration file exist, removing it")
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
    """
    Configure ICAT-related MySQL DB

    :param cfg: the configuration dict
    :param services: the services list (used to get the list of authentication plugins)
    """
    logger.info("Configuring SQL")
    sql = ""
    if 'icat' in services:
        t = cfg['icat_setup']['dbProperties'].split(':')
        d = dict([i.split("=") for i in t])
        sql = "create database " + d['databaseName'] + ";\n"
        sql += "grant all privileges on " + d['databaseName'] + ".* to " + d['user'] + "@localhost identified by '" + d['password'] + "';"
        run_sql(cfg, sql)

    # if you want to use DB-based auth, do also
    if 'authn_db' in services:
        t = cfg['authn_db_setup']['authn_dbProperties'].split(':')
        d = dict([i.split("=") for i in t])
        sql = "create database authn_db;\n"
        sql += "grant all privileges on authn_db.* to " + d['user'] + "@localhost identified by '" + d['password'] + "';"
        run_sql(cfg, sql)

    if 'topcat' in services:
        d = get_user_pwd(cfg['topcat_setup']['dbProperties'])
        sql = "create database topcat;\n"
        sql += "grant all privileges on " + d['databaseName'] + ".* to " + d['user'] + "@localhost identified by '" + d['password'] + "';"
        run_sql(cfg, sql)


def install_generic(service):
    """
    Generic, simple installer. 
    :param service: name of the service to be installed
    """
    global args
    logger.info("Installing %s" % service)
    old_pwd = os.getcwd()
    os.chdir(service)
    debug_flag = ""
    if args.debug > 2:
        debug_flag = "--verbose"
    try:
        check_call(["./setup", debug_flag, "configure"])
    except:
        logger.error("Cannot run ./setup for %s" % service)
        logger.error("Error: %s" % sys.exc_info()[1])
        sys.exit(-1)
    if service == 'icat':
        prop_fname = "icat.properties"
        try:
            check_call(['cp', prop_fname + ".example", prop_fname + ".example.orig"])
        except:
            logger.error("Cannot run mv for %s" % service)
            logger.error("Error: %s" % sys.exc_info()[1])
            sys.exit(-1)
    try:
        check_call(["./setup", debug_flag, "install"])
    except:
        logger.error("Cannot run ./setup for %s" % service)
        logger.error("Error: %s" % sys.exc_info()[1])
        sys.exit(-1)
    os.chdir(old_pwd)


def get_tars(cfg, services):
    """
    Download ICAT (and related) tars

    :param cfg: the configuration dict
    :param services: the services list (used to get the list of authentication plugins)
    """
    logger.info("Downloading archives from the Web")
    vers = cfg['version']
    url = 'http://www.icatproject.org/mvn/repo/org/icatproject'
    if 'icat' in services:
        get_archive('icat', ["wget", "-cq", url + "/icat.ear/" + vers['icat'] + "/icat.ear-" + vers['icat'] + "-distro.zip"])
    if 'authn_ldap' in services:
        get_archive('authn_ldap', ['wget', "-cq", url + '/authn_ldap/' + vers['authn_ldap'] + '/authn_ldap-' + vers['authn_ldap'] + '-distro.zip', ])
    if 'authn_db' in services:
        get_archive('authn_db', ['wget', "-cq", url + '/authn_db/' + vers['authn_db'] + '/authn_db-' + vers['authn_db'] + '-distro.zip', ])
    if 'icat-setup' in services:
        get_archive('icat-setup', ['wget', "-cq", url + '/icat-setup/' + vers['icat-setup'] + '/icat-setup-' + vers['icat-setup'] + '-distro.zip', ])
    if 'ice' in services:
        get_archive('ice', ['wget', "-cq", url + '/ice/' + vers['ice'] + '/ice-' + vers['ice'] + '-distro.zip', ])
    if 'topcat' in services:
        get_archive('topcat', ['wget', "-cq", url + '/TopCAT/' + vers['topcat'] + '/TopCAT-' + vers['topcat'] + '-distro.zip', ])
    logger.info("Done")


def get_archive(service, cmd):
    """
    Simple function to perform wget

    :param service: service to be retrieved
    :param cmd: full command to be executed
    """
    logger.debug("Getting " + service)
    try:
        out = check_output(cmd, )
    except CalledProcessError, e:
        logger.error("Error while downloading service %s" % (service))
        sys.exit(-1)


def test_icat(cfg, authn):
    """
    Testing ICAT installation
    
    :param cfg: configuration dict
    :param authn: authentication plugin to be used
    """
    if authn == "authn_db":
        logger.info("Test using " + authn + "")
        uname = cfg[authn]['users'].split(":")[0].split("=")[0]
        pwd = cfg[authn]['users'].split(":")[0].split("=")[1]
        print ['icat/testicat', 'https://' + HOSTNAME + ':8181', 'db', 'username', uname, 'password', pwd]
        P = call(['icat/testicat', 'https://' + HOSTNAME + ':8181', 'db', 'username', uname, 'password', pwd],)
        if P != 0:
            logger.error("Test using " + authn + " failed.")
            sys.exit(-1)
        logger.info("Test using " + authn + " succeeded")


def main():
    import argparse
    global args

    usage = """
    Simple ICAT installer. It will download, configure and install ICAT and various ICAT-related services, such as TopCAT, authn_db. It reads icat_installer.cfg in order to get all the needed configuration parameters.

    Example:
             python icat_installer --services authn_db,icat,topcat
"""

    parser = argparse.ArgumentParser(usage=usage)
    parser.add_argument("-n", "--nodownload", help="""Do not download files. general.tmp_dir should then point to where source directories are stored""",
                        action="store_true", default=False)
    parser.add_argument("--services", help="""Services to be installed (comma-separated list). Possible values: authn_db, authn_ldap, icat, icat-setup, ice, topcat""", action="store", default="")
    parser.add_argument("--debug", help="""Set debug level to: 0 (ERROR), 1 (WARNING), 2 (INFO), 3 (DEBUG). Default is WARNING""", nargs='?', action="store", default="1")
    args = parser.parse_args()

    if args.services == "":
        parser.print_help()
        sys.exit(-1)

    # setting debug level
    debug_level = {'0': logging.ERROR, '1': logging.WARNING, '2': logging.INFO, '3': logging.DEBUG}
    logger.setLevel(debug_level[args.debug])

    # basic configuration
    services = [s for s in args.services.split(",")]
    cfg = cfg_reader(CFG, services)

    # create tmp dir
    if not os.path.isdir(cfg['general']['tmp_dir']):
        os.mkdir(cfg['general']['tmp_dir'])
    os.chdir(cfg['general']['tmp_dir'])

    # Download
    if not args.nodownload:
        get_tars(cfg, services)
        unzip()

    # configure MYSQL
    configure_sql(cfg, services)

    # authn_db installation
    if "authn_db" in services:
        configure_authn(cfg, 'authn_db')
        install_generic('authn_db')
        sql = "use authn_db;\n"
        for up in cfg['authn_db']['users'].split(':'):
            sql += "INSERT INTO PASSWD (USERNAME, ENCODEDPASSWORD) VALUES ('" + up.split('=')[0] + "', '" + up.split('=')[1] + "');\n"
        try:
            run_sql(cfg, sql)
        except:
            logger.error("Failed to insert user accounts in authn_db... is it already configured and installed?")
            sys.exit(-1)

    # icat installation
    if 'icat' in services:
        configure_icat(cfg, services)
        install_generic('icat')
        authn_entries = [k for k in cfg.keys() if k.find("authn") != -1 and k.find("setup") == -1]
        for auth in authn_entries:
            test_icat(cfg, auth)

    # icat-setup installation
    if 'icat-setup' in services:
        if not os.path.isdir(os.environ["HOME"] + "/java"):
            logger.info("Creating directory ~/java")
            os.mkdir(os.environ["HOME"] + "/java")
        configure_generic(cfg, 'icat-setup')
        install_generic('icat-setup')
        logger.info("icat-setup installation DONE")

    # ice installation
    if 'ice' in services:
        logger.info("Installing ICE")
        p = call(['cp', 'ice/ice.properties',
                  cfg['setup']['glassfish'] + '/glassfish/domains/domain1/config'])
        p = call([cfg['setup']['glassfish'] + '/bin/asadmin',
                  'deploy', '--contextroot', 'ice', 'ice/ice-' + cfg['version']['ice'] + '.war'])
        logger.info("ICE installation DONE")

    # topcat installation
    if 'topcat' in services:
        configure_topcat(cfg)
        install_generic('topcat')

    logger.info("Installation completed!")


if __name__ == '__main__':
    main()
