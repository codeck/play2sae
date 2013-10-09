package org.codeck.play2sae

import sbt.{`package` => _, _}
import sbt.Keys._
import sbt.Keys.libraryDependencies

import com.earldouglas.xsbtwebplugin.WarPlugin
import com.earldouglas.xsbtwebplugin.PluginKeys.warPostProcess

object sbtPlugin extends Plugin
{
    lazy val saeSettings = Seq(
	  libraryDependencies += "org.codeck.play2sae" %% "play2-wrapper" % "0.2" excludeAll (
		ExclusionRule(organization = "com.typesafe.play"))
    ) ++ WarPlugin.warSettings ++ Seq(
	  warPostProcess in Compile <<= (target) map {
		(target) => { 
		  () =>
		  val webapp = target / "webapp"
		  IO.delete(webapp / "WEB-INF" / "lib" /"servlet-api-2.5.jar")
		}
	  }
	)
}




