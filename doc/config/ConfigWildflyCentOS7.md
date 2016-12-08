# **drtti**
## Distributed RealTime Target Intelligence
A distributed-input asynchronous feedback system for [EVE Online](http://www.eveonline.com/).

---

## Wildfly 10.1 on CentOS 7.x

---

### Install Wildfly 10.x

#### Before Starting:
* [Download](http://wildfly.org/downloads/) the wildfly-10.1.0.Final.tar.gz (or similar version; change version numbers below if necessary) file to your home directory

#### Install Wildfly as a systemd service

##### References:
* Unpacked _docs/contrib/scripts/systemd/README_ file

##### Definitions:
* Where: _**{SERVER}**_ is the FQDN or IP address of the server, like "_1.2.3.4_" or "_server.example.com_"

##### Instructions:
* From a root# shell
* Create a wildfly system user and group<br />(**-r**: system, **-g**: group, **-d**: homedir, **-s**: shell; see **man useradd** if needed)

        # groupadd -r wildfly
        # useradd -r -g wildfly -d /opt/wildfly -s /sbin/nologin wildfly

* Unpack the wildfly-10.1.0.Final.tar.gz to _/opt_ and change ownership to the new system user

        # tar xvfz /root/wildfly-10.1.0.Final.tar.gz -C /opt
        # chown -R wildfly:wildfly /opt/wildfly-10.1.0.Final

* Symlink _/opt/wildfly_ to the unpacked directory<br />(useful if later upgrading to something like 10.1.1 to avoid reconfig of paths)

        # ln -s /opt/wildfly-10.1.0.Final /opt/wildfly

* Add provided systemd config and launch files to system configuration in _/etc_ and _/opt/wildfly_

        # mkdir /etc/wildfly
        # cp /opt/wildfly/docs/contrib/scripts/systemd/wildfly.conf /etc/wildfly/
        # cp /opt/wildfly/docs/contrib/scripts/systemd/wildfly.service /etc/systemd/system/
        # cp /opt/wildfly/docs/contrib/scripts/systemd/launch.sh /opt/wildfly/bin/
        # chmod +x /opt/wildfly/bin/launch.sh

* Start Wildfly using systemd and enable it for system startup

        # systemctl start wildfly.service
        # systemctl enable wildfly.service

#### After Finishing:
* You should be able to reach the Wildfly server at http://{SERVER}:8080/
* The web-based admin console is only on **localhost** by default
* Maybe you want to [configure Wildfly to use SSL](ConfigUndertowSSL.md)