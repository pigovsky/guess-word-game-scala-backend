name := "guess word game backend"

version := "0.0.1"

scalaVersion := "2.11.1"

lazy val scalty = RootProject(uri("https://github.com/awesome-it-ternopil/scalty.git"))

libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-actor_2.11" % "2.4.17",
  "com.typesafe.akka" % "akka-http_2.11" % "10.0.10",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.10",
  "net.codingwell" %% "scala-guice" % "4.1.0"
)

lazy val root = (project in file("."))
  .dependsOn(scalty)