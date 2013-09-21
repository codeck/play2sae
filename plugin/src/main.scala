package org.codeck.play2sae

import sbt._

import com.earldouglas.xsbtwebplugin.WarPlugin

object sbtPlugin extends Plugin
{
    val saeSettings = Seq(
	  libraryDependencies += "javax.servlet" % "servlet-api" % "2.5"
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










