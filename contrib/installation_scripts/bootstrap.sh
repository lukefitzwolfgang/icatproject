#!/usr/bin/env bash
echo $USER
sudo apt-add-repository -y ppa:webupd8team/java
sudo apt-get -y update
# from here: https://github.com/seshendra/vagrant-ubuntu-tomcat7/blob/master/manifests/default.pp
echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections && echo debconf shared/accepted-oracle-license-v1-1 seen true | sudo debconf-set-selections
sudo apt-get install -y unzip oracle-java7-installer

echo "mysql-server mysql-server/root_password password password" | debconf-set-selections
echo "mysql-server mysql-server/root_password_again password password" | debconf-set-selections

sudo apt-get install -y mysql-client mysql-server
sudo apt-get install -y python-suds
sudo apt-get install -y python-mysql.connector

echo 'export GF_HOME="/opt/glassfish4/glassfish"' >> ~/.bashrc
export GF_HOME=/opt/glassfish4/glassfish

echo 'export JAVA_HOME="/usr/lib/jvm/java-7-oracle/"' >> ~/.bashrc
export JAVA_HOME="/usr/lib/jvm/java-7-oracle/"

export PATH=$PATH:$JAVA_HOME/bin:$GF_HOME/bin 

mkdir /opt/glassfish4
chown vagrant:vagrant  /opt/glassfish4
cd /vagrant/
chmod +x glassfish_setup_icat.sh
echo "Setting up Tomcat for ICAT"
sudo -u vagrant ./glassfish_setup_icat.sh -y -n -t -i /opt/glassfish4
sudo chown vagrant:vagrant -R /opt/glassfish4


