import org.codeck.play2sae.sbtPlugin //this line should be add on top

resolvers += Resolver.url("codeck repo", url("https://github.com/codeck/play2sae/raw/ivy-repo/"))(Resolver.ivyStylePatterns)

sbtPlugin.saeSettings

name := "xrpdb"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache
)     

libraryDependencies += "org.bouncycastle" % "bcprov-jdk15on" % "1.50"

play.Project.playScalaSettings
