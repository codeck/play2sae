import play.Project._
import org.codeck.play2sae.sbtPlugin

name := "testwar"

version := "1.0-SNAPSHOT"

resolvers += Resolver.url("codeck repo", url("https://github.com/codeck/play2sae/raw/ivy-repo/"))(Resolver.ivyStylePatterns)

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache
  ) 

playScalaSettings

sbtPlugin.saeSettings
