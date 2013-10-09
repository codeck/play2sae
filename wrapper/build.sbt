name := "play2-wrapper"

version := "0.2"

scalacOptions in Compile += "-deprecation"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.2.0" exclude ("javax.servlet", "servlet-api"),
  "javax.servlet" % "servlet-api" % "2.5"
) 
