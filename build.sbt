name := """play-java-starter-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava,PlayEbean)

scalaVersion := "2.12.6"

crossScalaVersions := Seq("2.11.12", "2.12.4")

libraryDependencies += guice

// Test Database
libraryDependencies += "com.h2database" % "h2" % "1.4.197"

// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.13"
libraryDependencies += javaJdbc
libraryDependencies += "com.google.code.gson" % "gson" % "2.8.5"
libraryDependencies +="org.mindrot" % "jbcrypt" % "0.3m"
libraryDependencies += "com.typesafe.play" %% "play-mailer" % "6.0.0"
libraryDependencies += "com.typesafe.play" %% "play-mailer-guice" % "6.0.0"
libraryDependencies += "com.sendgrid" % "sendgrid-java" % "4.3.0"
libraryDependencies ++= Seq(
  ws
)
libraryDependencies ++= Seq(evolutions, jdbc)

libraryDependencies += "com.auth0" % "java-jwt" % "3.7.0"


// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))

