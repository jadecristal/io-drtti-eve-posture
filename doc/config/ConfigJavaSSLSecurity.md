# **drtti**
## Distributed RealTime Target Intelligence
A distributed-input asynchronous feedback system for [EVE Online](http://www.eveonline.com/).

---

## Java SE VM SSL/TLS Security

---

### Install Bouncy Castle

#### Before Starting:
* Bouncy Castle is a JCE provider with EC/ECDH/ECDHE support
* You only _need_ to do this if you are using OpenJDK with EC _**missing**_
  * "_**missing**_" means not there, not just disabled in _java.security_
* You can use the Ciphers tool (references) to see what TLS profiles are enabled

#### Download and add the Bouncy Castle JCE provider
* Get the provider file for JDK 1.5-1.8
  * Currently it is [bcprov-jdk15on-155.jar](http://bouncycastle.org/download/bcprov-jdk15on-155.jar) - check yourself, though
  * The .jar file with -ext- has extra providers that you will not need

##### References:
* [Bouncy Castle latest releases](http://bouncycastle.org/latest_releases.html)
* [Atlassian Ciphers.java tool](https://confluence.atlassian.com/stashkb/list-ciphers-used-by-jvm-679609085.html)

##### Definitions:
* Where: _**{JAVA_HOME}**_ is your preferred JDK 8 install, like "_/usr/lib/jvm/java_"
* Where: _**{NUM}**_ is the next number after the previous security.provider, like "_9_"

##### Instructions:
* Place the downloaded file in _{JAVA_HOME}/jre/lib/ext_
* Edit _{JAVA_HOME}/jre/lib/security/java.security_ to add a security.provider

      security.provider.{NUM}=org.bouncycastle.jce.provider.BouncyCastleProvider

#### After Finishing:
* Note that this does not change which crypto suites and protocols are disabled

---

### Harden SSL Security

#### Before Starting:
* It will be assumed that you can find your way around a bit

#### Disable old crypto and upgrade DH primes
* Change which cryptographic algorithms are used
  * Old or broken hashing and crypto algorithms are bad for security
  * Short keys are bad for security
* Replace the default DH primes (might only work on OpenJDK)
  * DH usually (stupidly) shares a small set of prime numbers
  * Sharing prime numbers makes DHE weaker for secure key exchange
* Add a JAVA_OPTS line in the Wildfly config to use 2048-bit DH ephemerals
  * Short keys are bad for security

##### References:
* [SSLlabs Best Practices](https://github.com/ssllabs/research/wiki/SSL-and-TLS-Deployment-Best-Practices)
* [Oracle Java SE 8 Crypto Spec](https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html)
* [Oracle Java SE 8 JCE Standard Names](https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html)
* [Oracle Java SE 8 Sun JCE Providers](https://docs.oracle.com/javase/8/docs/technotes/guides/security/SunProviders.html)
* [OpenSSL wiki: Elliptic Curve Cryptography](https://wiki.openssl.org/index.php/Elliptic_Curve_Cryptography)
* [SSLlabs Server Test](https://www.ssllabs.com/ssltest/)
* [Stackoverflow "ECDHE cipher suites not supported on OpenJDK 8"](http://stackoverflow.com/questions/31971499/ecdhe-cipher-suites-not-supported-on-openjdk-8-installed-on-ec2-linux-machine)
* [Serverfault "How to generate new 2048 DH Params"](http://serverfault.com/questions/722182/how-to-generate-new-2048-bit-diffie-hellman-parameters-with-java-keytool)

##### Definitions:
* Where: _**{JAVA_HOME}**_ is your preferred JDK 8 install, like "_/usr/lib/jvm/java_"
* Where: _**{WILDFLY_HOME}**_ is your Wildfly base directory, like "_/opt/wildfly_"

##### Instructions:
* Edit _{JAVA_HOME}/jre/lib/security/java.security_ to set disabled algorithms
* Change the _jdk.tls.disabledAlgorithms_ property:

        jdk.tls.disabledAlgorithms=SSLv3, TLS1, MD5, SHA1, DSA, RC4, 3DES, 3DES_EDE_CBC, RSA keySize < 2048, DH keySize < 2048, EC keySize < 224

* Generate new Diffie-Hellman primes and save the text (this takes some time):

        # openssl dhparam -out dhparam2048.pem 2048
        # openssl dhparam -noout -text -check -in dhparam2048.pem | tr -d ':'

* Edit _{JAVA_HOME}/jre/lib/security/java.security_ to add new DH primes
* Add a value for the _jdk.tls.server.defaultDHEParameters_ property like:

        jdk.tls.server.defaultDHEParameters=
            { \
                123456781234567812345678123456 \
                0123456789abcdef0123456789abcd \
                   ... 18 lines total          \
                   ... do not forget the slash \
                0123456789abcdef0123456789abcd \
                ef01, 2 }

* Edit _{WILDFLY_HOME}/bin/standalone.conf_ to add extra JAVA_OPTS
* Add to JAVA_OPTS to set DH ephemerals to 2048 bits (end of file, # is comment):

        # Added to harden Diffie-Hellman key exchange for TLS
        JAVA_OPTS="$JAVA_OPTS -Djdk.tls.ephemeralDHKeySize=2048"

* Restart Wildfly

        # systemctl stop wildfly
        # systemctl start wildfly

#### After Finishing:
* Run the [SSLlabs test](https://www.ssllabs.com/ssltest/) against your SSL-secured server
  * Your server must be publicly available and running SSL on port 443
