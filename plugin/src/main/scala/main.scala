package org.codeck.play2sae

import sbt.{`package` => _, _}
import sbt.Keys._
import sbt.Keys.libraryDependencies

import com.earldouglas.xsbtwebplugin.WarPlugin
import com.earldouglas.xsbtwebplugin.PluginKeys.warPostProcess

import java.io.FileFilter

object sbtPlugin extends Plugin
{
  def wrapperVer = play2sae.BuildInfo.version

  val sae_local_all = Seq("commons-collections", 
						  "ezmorph",
						  "json-lib",
						  "commons-beanutils",
						  "commons-io",
						  "httpclient",
						  "commons-beanutils-bean-collections", 
						  "commons-lang",
						  "httpclient-cache",
						  "commons-beanutils-core",
						  "commons-logging",
						  "httpcore",
						  "mysql-connector-java",
						  "commons-codec",
						  "httpmime",
						  "sae-local").map(one => (one+"-.*\\.jar").r) ++
  Seq("activation",
	  "derby",
	  "log4j",
	  "mail").map(one => (one+".jar").r)

  def saeLibFilter = new FileFilter {
	override def accept(file: File) = {
	  sae_local_all.exists(
		_.findPrefixOf(file.getName()).isDefined
	  )
	}
  }

  def warMassage(appdir: File) {
	IO.delete(appdir / "WEB-INF" / "lib" /"servlet-api-2.5.jar")
	val webxml = appdir / "WEB-INF" / "web.xml"
	if (!webxml.exists()) {
	  IO.append(webxml, """<?xml version="1.0" ?>
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
        version="2.5">
  <display-name>APP by play2sae</display-name>
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
""")
	}
  }

  lazy val saeSettings = Seq(
	libraryDependencies += "org.codeck.play2sae" %% "play2-wrapper" % wrapperVer excludeAll (
	  ExclusionRule(organization = "com.typesafe.play"))
  ) ++ WarPlugin.warSettings ++ Seq(
    javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.6", "-target", "1.6"),
	warPostProcess in Compile := {
	  () =>
	  val webapp = target.value / "webapp"
	  warMassage(webapp)
	  IO.listFiles(webapp / "WEB-INF" / "lib", saeLibFilter).map(
		l => IO.delete(l)
	  )
	}
  )

  lazy val saeLocalSettings = Seq(
	libraryDependencies += "org.codeck.play2sae" %% "play2-wrapper" % wrapperVer excludeAll (
	  ExclusionRule(organization = "com.typesafe.play"))
  ) ++ WarPlugin.warSettings ++ Seq(
    javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.6", "-target", "1.6"),
	warPostProcess in Compile := {
	  () =>
	  val webapp = target.value / "webapp"
	  warMassage(webapp)
	}
  )

}
