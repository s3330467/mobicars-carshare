# mobicars
Welcome to Mobi-Cars
===================

Java based api for  Mobi-Cars.

Requirements
-------------
 - Java 8
 - IntelliJ
 - Tomcat Webserver
 - Maven
 - MySQL

Build
-------------

* To build a deployable war file :
> ``` $ ./mvnw clean package ``` .



Deploy
-------
* To deploy the war file to a standalone tomcat server
> Copy the `mobicars-api.war` generated on the target folder to
> $CATALINA_HOME/webapps


Verify Deployment
-----------------
* Use the test api
> ``` localhost:8080/mobicars-api/hello```
>
> OR
>
> ``` localhost:8080/hello/<name>```
