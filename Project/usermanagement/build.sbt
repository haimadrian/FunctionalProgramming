name := "usermanagement"

version := "1.0"

lazy val `usermanagement` = (project in file(".")).enablePlugins(PlayScala)


resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

scalaVersion := "2.13.5"

// Enable injected routes as we use dependency injection for our mongo controllers
routesGenerator := InjectedRoutesGenerator

libraryDependencies += "com.google.firebase" % "firebase-admin" % "8.0.1"
libraryDependencies += "commons-io" % "commons-io" % "20030203.000550"
libraryDependencies += "org.mongodb.scala" % "mongo-scala-driver_2.13" % "4.3.2"
libraryDependencies ++= Seq(jdbc, ehcache, ws, specs2 % Test, guice)
