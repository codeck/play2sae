import play.Project._

name := "testwar"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache
  )     

playScalaSettings
