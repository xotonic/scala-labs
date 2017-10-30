import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "by.xotonic",
      scalaVersion := "2.12.3",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Lab",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2" ,
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "org.scalactic" %% "scalactic" % "3.0.4"
    )
  )
