# Tomcat

Apache implementation of Java Servlet and JavaServer Pages,
which is a subset of EE.

Homepage: <http://tomcat.apache.org/>

Documentation: <http://tomcat.apache.org/tomcat-7.0-doc/>

This cheat is about Tomcat 7 by default.

## Ubuntu install

Run:

    sudo apt-get install tomcat7 tomcat7-admin tomcat7-docs tomcat7-examples

The `$TOMCAT_HOME` is `/var/lib/tomcat7`.

Then edit:

    sudo vim /etc/default/tomcat7

and set:

    export JAVA_HOME='/usr/lib/jvm/java-8-oracle'

Then:

    sudo service start tomcat7

and:

    firefox localhost:8080
    firefox localhost:8080/docs
    firefox localhost:8080/examples

To compile, you must tell them where Tomcat's `servlet-api.jar` is located. On Ubuntu 14.04 use:

    javac -cp '.:/usr/share/tomcat7/lib/servlet-api.jar' ServletHelloWorld.java

## Admin app

Allows you to do thing like start and stop other apps.

By default, new available apps are turned off, so you really need to install this.

    firefox localhost:8080/manager/html

But to do that you first need to create an user with enough privileges:

    sudo vim /var/lib/tomcat7/conf/tomcat-users.xml

And add inside `<tomcat-users>`:

    <role rolename="manager-gui"/>
    <role rolename="manager"/>
    <role rolename="admin"/>
    <user username="admin" password="admin" roles="admin,manager,manager-gui"/>

Restart, and now you can login with username as password `admin`.

## Development server

TODO is there a simpler way of running WAR applications for development than copying it around?
<http://serverfault.com/questions/658773/development-server-for-java-war-applications-from-current-directory-and-without>

## Deploy apps

On Ubuntu 14.04, the default index is located at:

    /var/lib/tomcat7/webapps/ROOT/index.html

The most common way to deploy apps is by putting WAR file under:

    /var/lib/tomcat7/webapps

But you can also put the unpacked WAR file there.

### Disable certain applications from starting up by default

Hard:

- <http://stackoverflow.com/questions/5067062/how-to-disable-specific-apps-from-starting-during-tomcat-startup>
- <http://serverfault.com/questions/333375/is-it-possible-to-turn-off-auto-start-for-some-but-not-all-app-servers-deploye>

Corrupting the apps by renaming key files like `WEB-INF` is the best plan I've found so far.

## Logs

Ubuntu 14.04:

    less /var/log/tomcat7/catalina.out

## Environment variables

TODO: should they be set at: `/etc/default/tomcat7` or `/usr/share/tomcat7/setenv.sh`?

<http://stackoverflow.com/questions/24803037/tomcat-7-setenv-sh-vs-etc-default-tomcat7-to-update-java-opts>

Probably `etc` is better, since the file already exists.

`setenv.sh` method:

    cd /usr/share/tomcat7/bin
    printf '#!/usr/bin/env bash
    export JAVA_HOME="/usr/lib/jvm/java-7-oracle"
    export CATALINA_OPTS="-Xmx1024M -XX:PermSize=1024M"
    ' | sudo tee setenv.sh
    sudo chmod +x setenv.sh

### CATALINA_OPTS

Get passed directly to the JVM that Tomcat will run on, so they same options as for the `java` executable.

### JAVA_OPTS

TODO vs `CATALINA_OPTS`?
<http://stackoverflow.com/questions/11222365/catalina-opts-vs-java-opts-what-is-the-difference>

Apparently, both are used in the Tomcat start script, but only `CATALINA_OPTS` gets passed to the stop script.

Also `JAVA_OPTS` may be used for multiple programs, while `CATALINA_OPTS` is Tomcat specific.

## Configuration file

`/etc/tomcat7/server.xml`:

-   `Host`:

    -   `appBase="webapps"`

    -   `unpackWARs="true"`

    -   `autoDeploy="true"`: deploy every WAR under `appBase` by default.

    -   `deployIgnore="foo.*"`: don't deploy apps under `appBase` whose names start with `foo`.
        They will not show in the manager app either, so it's hard to start them.

TODO: how to disable auto-start of certain apps only? <http://stackoverflow.com/questions/5067062/how-to-disable-specific-apps-from-starting-during-tomcat-startup>
