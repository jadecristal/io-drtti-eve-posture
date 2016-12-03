# **drtti**
## Distributed RealTime Target Intelligence
A distributed-input asynchronous feedback system for [EVE Online](http://www.eveonline.com/).

---

## JEE/DB Deployment Configuration

---

TODO:Put all of these commands in JEE CLI batch scripts

### SSL Certificates

* Get SSL certificates from _letsencrypt.org_
* [Build a pkcs12 keystore](ConfigLetsEncrypt.md) for the JEE server to use

### JBossEAP / Wildfly Configuration

* Download and [install](ConfigWildflyCentOS7.md) the JEE server you plan to use
* [Configure the web container](ConfigUndertowSSL.md) to use SSL with your keystore
* [Set up the MySQL driver and JNDI datasource](ConfigDatasourceMySQL.md) for Hibernate/JPA
* Make sure to [enable and configure ActiveMQ Artemis](ConfigActiveMQArtemisJMS.md) for JMS

---

TODO:Put all of these commands in SQL scripts

### Database Configuration

* TODO:Add database create and user addition instructions