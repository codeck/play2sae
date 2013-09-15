addSbtPlugin("org.ensime" % "ensime-sbt-cmd" % "0.1.2")

// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository 
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2-SNAPSHOT")

//addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "0.6.0")

addSbtPlugin("com.earldouglas" % "xsbt-web-plugin" % "0.4.2")

