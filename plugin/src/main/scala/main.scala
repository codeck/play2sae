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

  lazy val saeSettings = Seq(
	libraryDependencies += "org.codeck.play2sae" %% "play2-wrapper" % wrapperVer excludeAll (
	  ExclusionRule(organization = "com.typesafe.play"))
  ) ++ WarPlugin.warSettings ++ Seq(
    javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.6", "-target", "1.6"),
	warPostProcess in Compile := {
	  () =>
	  val webapp = target.value / "webapp"
	  IO.delete(webapp / "WEB-INF" / "lib" /"servlet-api-2.5.jar")
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
	  IO.delete(webapp / "WEB-INF" / "lib" /"servlet-api-2.5.jar")
	}
  )

}
