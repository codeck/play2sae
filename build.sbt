version in ThisBuild := "0.3"

scalaVersion in ThisBuild := "2.10.2"

publishTo in ThisBuild := Some(Resolver.file("dest-repository", file("target/local-repo"))(Resolver.ivyStylePatterns))

publishMavenStyle in ThisBuild := false

organization in ThisBuild := "org.codeck.play2sae"

javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.6", "-target", "1.6")

