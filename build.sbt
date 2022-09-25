ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := Common.scalaVersion

Global / onChangedBuildSource := ReloadOnSourceChanges

scalafmtFilter := "diff-dirty"

addCommandAlias("fmt", "all root/scalafmtSbt root/scalafmtAll")
addCommandAlias("fmtCheck", "all root/scalafmtSbtCheck root/scalafmtCheckAll")
addCommandAlias("check", "; scalafmtSbtCheck; scalafmtCheckAll")

lazy val root = (project in file("."))
  .enablePlugins(DockerPlugin)
  .settings(
    name := "FunctionalProject",
    resolvers := Common.resolvers,
    libraryDependencies ++= Seq(
      // scalatest
      "org.scalactic" %% "scalactic" % Common.scalaTestVersion,
      "org.scalatest" %% "scalatest" % Common.scalaTestVersion % Test,
      // scalamock
      "org.scalamock" %% "scalamock" % Common.scalaMockVersion % Test,
      //zio
      "dev.zio" %% "zio"         % Common.zioVersion,
      "dev.zio" %% "zio-streams" % Common.zioVersion,
    ),
  )
