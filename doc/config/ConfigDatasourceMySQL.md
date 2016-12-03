# **drtti**
## Distributed RealTime Target Intelligence
A distributed-input asynchronous feedback system for [EVE Online](http://www.eveonline.com/).

---

## MySQL Database Driver and JNDI Data Source

---

### Install the MySQL Database Driver

#### Before Starting:
* Download: the MySQL Connector/J archive from [https://dev.mysql.com/](https://dev.mysql.com/)
* Unpack: the archive into some location where you'll know how to find it

#### Install the mysql-connector-java JAR as a module

##### Definitions:
* Where: _**{PATH}**_ is where you unpacked the Connector/J file, like "_/path/mysql-connector-java-5.1.40_"

##### Instructions:
* Use jboss-cli.sh shell

      [standalone@embedded /] module add --name=com.mysql --slot=main --resources={PATH}/mysql-connector-java-5.1.40-bin.jar --dependencies=javax.api,javax.transaction.api
      [standalone@embedded /] cn /subsystem=datasources/
      [standalone@embedded subsystem=datasources] ./jdbc-driver=mysql:add(driver-name=mysql, driver-module-name=com.mysql, driver-class-name=com.mysql.jdbc.Driver, driver-xa-datasource-class-name=com.mysql.jdbc.jdbc2.optional.MysqlXADataSource)

---

### Create a JNDI Data Source

#### Before Starting:
* Make sure the com.mysql.jdbc.Driver is installed as a module

#### Add a JNDI data source to the JEE server and configure connection pooling

##### Definitions:
* Where: _**{NAME}**_ is the name you want to give the data source, like "_MyDataSource_"
* Where: _**{DB}**_ is the name of the database on the server, like "_application_db_"
* Where: _**{DBUSER}**_ is the login user name on the server, like "_app_user_"
* Where: _**{DBPASS}**_ is the password of the login user on the server, like "_U$3rP@s$_"
* Where: _**{DBSERVER}**_ is the FQDN or IP address of the DB server, like "_1.2.3.4_" or "_db.example.com_"
* Where: _**{DBPORT}**_ is the TCP/IP port the server listens on, like "_3306_"

##### Instructions:
* Use jboss-cli.sh shell

      [standalone@embedded /] data-source add --name={NAME} --driver-name=mysql --user-name={DBUSER} --password={DBPASS} --connection-url="jdbc:mysql://{DBSERVER}:{DBPORT}/{DB}?autoReconnect=true&useSSL=false" --jndi-name=java:jboss/datasources/{NAME}

* TODO:Add connection pooling instructions
* TODO:Add connection test command instructions