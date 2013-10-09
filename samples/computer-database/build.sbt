import play.Project._
import org.codeck.play2sae.sbtPlugin

name := "computer-database"

version := "1.0"

libraryDependencies ++= Seq(jdbc, anorm)

playScalaSettings

resolvers += Resolver.url("codeck repo", url("https://github.com/codeck/play2sae/raw/ivy-repo/"))(Resolver.ivyStylePatterns)

sbtPlugin.saeSettings