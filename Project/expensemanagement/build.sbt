name := "expensemanagement"

version := "1.0"

scalaVersion := "2.13.5"

// expensemanagement depends on common
dependsOn(ProjectRef(file("../expensecommon"), "exportedCommonLib"))

lazy val `expensemanagement` = (project in file(".")).enablePlugins(PlayScala)


resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

// Enable injected routes as we use dependency injection for our mongo controllers
routesGenerator := InjectedRoutesGenerator

libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "4.3.2"

// Get Apache's bless: Constants for Http headers / media types
libraryDependencies += "org.apache.httpcomponents" % "httpcore" % "4.4.14"

// So we can verify admin JWT (The %% will append _2.13 to module name. (Scala version))
libraryDependencies += "com.github.jwt-scala" %% "jwt-play-json" % "9.0.1"

libraryDependencies ++= Seq(jdbc, ehcache, ws, specs2 % Test, guice)

herokuAppName in Compile := "expenseapphit"
