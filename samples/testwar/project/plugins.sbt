addSbtPlugin("org.ensime" % "ensime-sbt-cmd" % "0.1.2")

// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository 
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2-SNAPSHOT")

resolvers += Resolver.url("codeck repo", url("https://github.com/codeck/play2sae/raw/ivy-repo/"))(Resolver.ivyStylePatterns)

addSbtPlugin("org.codeck.play2sae" % "sbt-plugin" % "0.1")

