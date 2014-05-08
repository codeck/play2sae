play2sae
========

sbt plugin for deploying play2.2 applications to Sina App Engine (with jetty7.4.x)

Requirements
------
* scala 2.10+
* Play 2.2.3
* sbt 0.13+

HowTo
------

###Step 1: Start
create a play application by *$play new YourSaeAppName* or skip this step when porting existing project.

###Step 2: Add plugin
add the following lines to *'YourSaeAppName/project/plugins.sbt'*,

```scala
resolvers += Resolver.url("codeck repo", url("https://github.com/codeck/play2sae/raw/ivy-repo/"))(Resolver.ivyStylePatterns)

addSbtPlugin("org.codeck.play2sae" % "sbt-plugin" % "0.3.3")
```

add the following lines to *'YourSaeAppName/build.sbt'*,

```scala
import org.codeck.play2sae.sbtPlugin //this line should be add on top

resolvers += Resolver.url("codeck repo", url("https://github.com/codeck/play2sae/raw/ivy-repo/"))(Resolver.ivyStylePatterns)

sbtPlugin.saeSettings

```

####Step 2.1: Add customized web.xml (Optional)
play2sae will create a webxml when packaging.

create *'YourSaeAppName/app/webapp/WEB-INF/web.xml'* when you need a customized web.xml

####Step 2.2: Add customized database setting (Optional)
play2sae will create default db with info from [SaeUserInfo](http://sae4java.sinaapp.com/doc/com/sina/sae/util/SaeUserInfo.html)

edit *'YourSaeAppName/conf/application.conf'* when you need a customized web.xml (here is a [template](https://github.com/codeck/play2sae/blob/master/samples/testwar/app/webapp/WEB-INF/web.xml))

####Step 2.3: Add SAE sdks (Optional)
extract [sae-1.1.0-all.zip](http://sae4java.sinaapp.com/lib/sae-1.1.0-all.zip) to *'YourSaeAppName/lib'" when you need SAE sdks.

play2sae will automatically strip sdk jars from final .war file.


###Step 3: **package**
**"package"** command in Play console are ready to package .war file for SAE now.

Samples
------
simplest app(by "$play new"), computer-database and zentasks(TODO) are deployed for demo

See http://testwar.sinaapp.com

Acknowledge
------

original (servlet wrapper) code from https://github.com/dlecan/play2-war-plugin with modification for play2.2

(war) packaging task by https://github.com/JamesEarlDouglas/xsbt-web-plugin
