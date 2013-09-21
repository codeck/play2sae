play2sae
========

sbt plugin for deploying play2.2 applications to Sina App Engine (with jetty7.4.x)

Requirements
------
* scala 2.10+
* Play 2.2+
* sbt 0.13+

HowTo
------

###Step 1: Start
create a play application by *$play new YourSaeAppName*

###Step 2: Add plugin
add the following lines to *'YourSaeAppName/project/plugins.sbt'*,

```scala
resolvers += Resolver.url("codeck repo", url("https://github.com/codeck/play2sae/raw/ivy-repo/"))(Resolver.ivyStylePatterns)

addSbtPlugin("org.codeck.play2sae" % "sbt-plugin" % "0.1")
```

add the following lines to *'YourSaeAppName/build.sbt'*,

```scala
import org.codeck.play2sae.sbtPlugin

resolvers += Resolver.url("codeck repo", url("https://github.com/codeck/play2sae/raw/ivy-repo/"))(Resolver.ivyStylePatterns)

sbtPlugin.saeSettings

```

###Step 3: Add web.xml
create *'YourSaeAppName/app/webapp/WEB-INF/web.xml'* with the following content,

```xml
<?xml version="1.0" ?>
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
        version="2.5">

  <display-name>YourSaeAppName</display-name>

  <listener>
      <listener-class>play.core.server.servlet25.Play2Servlet</listener-class>
  </listener>

  <servlet>
    <servlet-name>play</servlet-name>
    <servlet-class>play.core.server.servlet25.Play2Servlet</servlet-class>
  </servlet>

  <servlet-mapping>
    <servlet-name>play</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>

</web-app>
```

###Step 4: **package** 
**"package"** command in Play console are ready to package .war file for SAE now.

Acknowledge
------

original (servlet wrapper) code from https://github.com/dlecan/play2-war-plugin with modification for play2.2

(war) packaging task by https://github.com/JamesEarlDouglas/xsbt-web-plugin
