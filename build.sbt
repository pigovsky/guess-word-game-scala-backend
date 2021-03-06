name := "guess-word-game-backend"

organization := "pigovsky"

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

enablePlugins(DockerPlugin)

dockerfile in docker := {
  // The assembly task generates a fat JAR file
  val artifact: File = assembly.value
  val artifactTargetPath = s"/app/${artifact.name}"

  new Dockerfile {
    from("java")
    add(artifact, artifactTargetPath)
    expose(8080)
    entryPoint("java", "-jar", artifactTargetPath)
  }
}

imageNames in docker := Seq(
  // Sets the latest tag
  ImageName(s"${organization.value}/${name.value}:latest")
)
