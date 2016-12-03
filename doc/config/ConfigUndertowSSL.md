# **drtti**
## Distributed RealTime Target Intelligence
A distributed-input asynchronous feedback system for [EVE Online](http://www.eveonline.com/).

---

## Web Container SSL Configuration

---

### Undertow (Web Container) SSL Setup

#### Configure the JEE server to use the _letencrypt.org_ keystore for SSL

#### Before Starting:
* You need to have a pkcs12 or jks keystore ready with SSL certificates
* You need to have [already built your keystore](ConfigLetsEncrypt.md) from letsencrypt.org
* Copy the keystore to _/opt/wildfly/standalone/_ assuming you're using Wildfly 10.x

##### Definitions:
* Where: _**{DOMAIN}**_ is the name of your domain, like "_example.com_"
* Where: _**{PASSWORD}**_ is the password you're using for the keystore, like "_U$3rP@s$_"

##### Instructions: 
* Use jboss-cli.sh shell (replace **{domain}** with your domain)
* Add a security realm called SSLRealm:

      [standalone@embedded /] cn /core-service=management
      [standalone@embedded core-service=management] ./security-realm=SSLRealm/:add()
      [standalone@embedded core-service=management] cn security-realm=SSLRealm
      [standalone@embedded security-realm=SSLRealm] ./server-identity=ssl/:add(keystore-path=letsencrypt-{DOMAIN}.pkcs12, keystore-relative-to=jboss.server.config.dir, alias=letsencrypt-{DOMAIN}, keystore-password={PASSWORD})

* Modify the default server in the undertow subsystem to use the new security realm:

      [standalone@embedded /] cn /subsystem=undertow/server=default-server
      [standalone@embedded server=default-server] ./https-listener=default-https/:add(socket-binding=https, security-realm=SSLRealm)

* Optionally, modify the management interface to use SSL:

      [standalone@embedded /] cn /core-service=management/security-realm=ManagementRealm
      [standalone@embedded security-realm=ManagementRealm] ./server-identity=ssl/:add(keystore-path=letsencrypt-{DOMAIN}.pkcs12, keystore-relative-to=jboss.server.config.dir, alias=letsencrypt-{DOMAIN}, keystore-password={PASSWORD})
      [standalone@embedded security-realm=ManagementRealm] cn /core-service=management/management-interface=http-interface
      [standalone@embedded management-interface=http-interface] :write-attribute(name=secure-socket-binding, value=management-https)