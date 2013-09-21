scalaVersion in ThisBuild := "2.10.2"

publishTo in ThisBuild := Some(Resolver.file("dest-repository", new File("target/local-repo")))

publishMavenStyle in ThisBuild := true

organization in ThisBuild := "org.codeck.play2sae"

