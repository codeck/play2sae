import sbt._
import Keys._


object rootBuilder extends Build {
  lazy val play2sae =
    project.in( file(".") )
	  .aggregate(wrapper, plugin)
  lazy val plugin = project.in(file("plugin"))
  lazy val wrapper = project.in(file("wrapper"))
}
