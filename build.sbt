ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := Common.scalaVersion

Global / onChangedBuildSource := ReloadOnSourceChanges

addCommandAlias("fmt", "all root/scalafmtSbt root/scalafmtAll")
addCommandAlias("fmtCheck", "all root/scalafmtSbtCheck root/scalafmtCheckAll")
addCommandAlias("check", "; scalafmtSbtCheck; scalafmtCheckAll")
addCommandAlias("publishKind", "fmt; clean; assembly")

scalafmtOnCompile := true

// TODO: Make these settings common
lazy val root = (project in file("."))
  .enablePlugins(DockerPlugin, ScalafmtPlugin)
  .settings(
    name := "FunctionalProject",
    assembly / assemblyJarName := project.id + "-uber.jar",
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
