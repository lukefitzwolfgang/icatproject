#!/usr/bin/env python
from optparse import OptionParser
import sys
import shutil
import os

def abort(msg):
    print >> sys.stderr, msg
    sys.exit(1)
    
root = os.getuid() == 0 

parser = OptionParser("usage: %prog [options] install | uninstall")
default = '/usr/share' if root else '~/java'
parser.add_option("--appDir", "-a", help="location to store java applications [" + default + "]", default=default)

default = '/usr/bin' if root else '~/bin'
parser.add_option("--binDir", "-b", help="location to store executables [" + default + "]", default=default)

options, args = parser.parse_args()

if len(args) != 1:abort("Must have one argument: 'install' or 'uninstall'")

cmd = args[0].upper()
if cmd not in ["INSTALL", "UNINSTALL"]: abort("Must have one argument: 'install' or 'uninstall'")

appDir = os.path.expanduser(options.appDir)
binDir = os.path.expanduser(options.binDir)

if not os.path.exists ("setup.py"): abort ("This must be run from the unpacked distribution directory")

if cmd == "INSTALL":
    
    try:
        path = os.path.join(appDir, "icat-setup")
        if os.path.exists(path):
            shutil.rmtree(path)
        os.mkdir(path)
        
        for file in os.listdir("."):
            ext = os.path.splitext(file)[1]
            if ext == ".jar":
                shutil.copy(file, path)
                
        file = os.path.join(binDir, "icat-setup")
        with open(file, "w") as f:
            print >> f, "#!/bin/sh"
            print >> f, 'java -cp "' + path + '/*" Setup $*'
            
        os.chmod(file, 0755)

    except Exception as e:
        abort(str(e))
        
else:  # UNINSTALL
    
    try:
        path = os.path.join(appDir, "icat-setup")
        if os.path.exists(path):
            shutil.rmtree(path)
            
        file = os.path.join(binDir, "icat-setup")    
        if os.path.exists(file): os.remove(file)
    
    except Exception as e:
        abort(str(e))       
    
            
    
