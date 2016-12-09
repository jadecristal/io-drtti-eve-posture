# **drtti**
## Distributed RealTime Target Intelligence
A distributed-input asynchronous feedback system for [EVE Online](http://www.eveonline.com/).

---

## Web Container SSL Configuration

---

### Undertow (Web Container) SSL Setup (Wildfly 10.x)

#### Before Starting:
* You need to have a pkcs12 or jks keystore ready with SSL certificates
* You need to have [already built your keystore](ConfigLetsEncrypt.md) from letsencrypt.org
* Copy the keystore to _/opt/wildfly/standalone/configuration_, assuming you're using Wildfly 10.x

#### Configure the JEE server to use the _letencrypt.org_ keystore for SSL

##### References:
* [Wildfly 10 Admin Guide: Enable SSL](https://docs.jboss.org/author/display/WFLY10/Admin+Guide#AdminGuide-EnableSSL)
* [Stackoverflow "Wildfly 9 http to https"](http://stackoverflow.com/questions/32008182/wildfly-9-http-to-https)

##### Definitions:
* Where: _**{DOMAIN}**_ is the name of your domain, like "_example.com_"
* Where: _**{PASSWORD}**_ is the password you're using for the keystore, like "_U$3rP@s$_"

##### Instructions: 
* Use jboss-cli.sh shell (replace **{domain}** with your domain)
* Reconfigure the built-in security realm called ApplicationRealm: 

        [standalone@embedded /] cn /core-service=management/security-realm=ApplicationRealm/server-identity=ssl
        [standalone@embedded server-identity=ssl] :write-attribute(name=keystore-path, value=letsencrypt-{DOMAIN}.pkcs12)
        [standalone@embedded server-identity=ssl] :write-attribute(name=keystore-password, value={PASSWORD})
        [standalone@embedded server-identity=ssl] :write-attribute(name=alias, value=letsencrypt-{DOMAIN})
        [standalone@embedded server-identity=ssl] :undefine-attribute(name=key-password)
        [standalone@embedded server-identity=ssl] :undefine-attribute(name=generate-self-signed-certificate-host)

#### After Finishing:
* Internet Explorer 11 probably works with SSL
* Wildfly ships with HTTP/2 turned on on the connectors
  * Chrome and Firefox give SSL errors due to the crypto setup
  * Go [harden the SSL configuration](ConfigJavaSSLSecurity.md) to make them stop complaining
