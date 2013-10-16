package org.codeck.play2sae

import sbt.{`package` => _, _}
import sbt.Keys._
import sbt.Keys.libraryDependencies

import com.earldouglas.xsbtwebplugin.WarPlugin
import com.earldouglas.xsbtwebplugin.PluginKeys.warPostProcess

object sbtPlugin extends Plugin
{
  def wrapperVer = play2sae.BuildInfo.version

  lazy val saeSettings = Seq(
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




