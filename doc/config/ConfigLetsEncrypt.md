# **drtti**
## Distributed RealTime Target Intelligence
A distributed-input asynchronous feedback system for [EVE Online](http://www.eveonline.com/).

---

## _Let's Encrypt_ SSL Certificates

---

### Get SSL Certificates from _letsencrypt.org_

#### Before Starting _(these are outside the scope of this document)_:
* Sign up for [_letsencrypt.org_](https://www.letsencrypt.org)
* Install [_certbot_](https://certbot.eff.org/) from [EPEL](https://fedoraproject.org/wiki/EPEL)
* Run _certbot_ to verify your domain and get SSL certificates

#### Build a pkcs12 keystore from _letsencrypt.org_ certificates
* A newer JEE server is required to support pkcs12 keystores
  * If your JEE server doesn't support pkcs12 keystores, use the java _keystore_ tool
  * The _keystore_ tool requires different formats for certificates
  * Converting non-PEM encoded files to work with _keytool_ is outside the scope of this document

##### Definitions:
* Where: _**{DOMAIN}**_ is the name of your domain, like "_example.com_"
* Where: _**EAP_HOME**_ is the root of the JBossEAP/Wildfly server install, like "_/opt/wildfly_" or "_/home/username/jboss-eap-7.0.0_"
* Where: _**{PASSWORD}**_ is the password you're using for the keystore, like "_U$3rP@s$_"

##### Instructions:
* From: _/etc/letsencrypt/live/**{DOMAIN}**_:

      [user@hostname {DOMAIN}]$ openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out letsencrypt-{DOMAIN}.pkcs12 -name letsencrypt-{DOMAIN} -CAfile chain.pem -caname letsencrypt-root

* Provide: a password, which you'll need later on and should probably remember

[//]: # (* Copy: the keystore &lpar;_letsencrypt-**{DOMAIN}**.pkcs12_&rpar; to _**EAP_HOME**/standalone/configuration_ and chmod it 700)