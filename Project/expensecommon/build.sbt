name := "expensecommon"

version := "1.0"

scalaVersion := "2.13.5"

lazy val exportedCommonLib = (project in file("."))

lazy val `expensecommon` = (project in file(".")).enablePlugins(PlayScala)


resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "4.3.2"
libraryDependencies ++= Seq(ws, specs2 % Test, guice)
