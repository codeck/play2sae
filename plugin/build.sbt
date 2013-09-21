sbtPlugin := true

name := "sbt-plugin"

version := "0.1"

scalacOptions in Compile += "-deprecation"

libraryDependencies += "com.earldouglas" % "xsbt-web-plugin" % "0.4.2" extra ("scalaVersion" -> CrossVersion.binaryScalaVersion("2.10.2"),  "sbtVersion" -> "0.13")
