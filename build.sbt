scalaVersion in ThisBuild := "2.10.2"

publishTo in ThisBuild := Some(Resolver.file("dest-repository", file("target/local-repo"))(Resolver.ivyStylePatterns))

publishMavenStyle in ThisBuild := false

organization in ThisBuild := "org.codeck.play2sae"

