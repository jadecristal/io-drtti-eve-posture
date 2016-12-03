# **drtti**
## Distributed RealTime Target Intelligence
A distributed-input asynchronous feedback system for [EVE Online](http://www.eveonline.com/).

---

## ActiveMQ Artemis (JMS) Setup

---

### Remote Acceptor and SSL

#### Add a remote acceptor that uses SSL to ActiveMQ Artemis

#### Before Starting:
* ActiveMQ Artemis needs to be enabled by either:
  * Using a JEE Full profile configuration (_e.g. standalone-full.xml_)
  * Adding the _messaging-activemq_ subsystem to one of the web profile configs
* You need to have [already built your keystore](ConfigLetsEncrypt.md) from letsencrypt.org

##### References:
* [https://developer.jboss.org/thread/272818?_sscc=t](https://developer.jboss.org/thread/272818?_sscc=t)
* [https://docs.jboss.org/author/display/WFLY10/Admin+Guide](https://docs.jboss.org/author/display/WFLY10/Admin+Guide)

##### Definitions:
* Where: _**{DOMAIN}**_ is the name of your domain, like "_example.com_"
* Where: _**{PASSWORD}**_ is the password you're using for the keystore, like "_U$3rP@s$_"

##### Instructions:
* Use jboss-cli.sh shell (replace **{domain}** with your domain)
* Add a socket binding for remote connections:

      [standalone@embedded /] cn /socket-binding=standard-sockets/
      [standalone@embedded socket-binding=standard-sockets] ./socket-binding=artemis-ssl/:add(port=61617)

* Configure the socket binding for SSL:

      [standalone@embedded /] cn /subsystem=messaging-activemq/server=default
      [standalone@embedded server=default] ./remote-connector=artemis-ssl-connector/:add(socket-binding=artemis-ssl)
      [standalone@embedded server=default] cn /remote-connector=artemis-ssl-connector/socket-binding=artemis-ssl/
      [standalone@embedded socket-binding=artemis-ssl] :write-attribute(name=params, value={ ssl-enabled => true, key-store-path => ${jboss.server.config.dir}/letsencrypt-{DOMAIN}.pkcs12, key-store-password => {PASSWORD})