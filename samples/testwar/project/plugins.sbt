addSbtPlugin("org.ensime" % "ensime-sbt-cmd" % "0.1.2")

// Comment to get more information during initialization
//logLevel := Level.Warn
logLevel := Level.Debug

// The Typesafe repository 
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % System.getProperty("play.version"))

//resolvers += Resolver.url("codeck repo", url("https://github.com/codeck/play2sae/raw/ivy-repo/"))(Resolver.ivyStylePatterns)
resolvers += Resolver.file("test repo", file("/home/kring/play2sae/@github/target/local-repo"))(Resolver.ivyStylePatterns)


addSbtPlugin("org.codeck.play2sae" % "sbt-plugin" % "0.3.3")

