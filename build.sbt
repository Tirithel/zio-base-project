ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := Dependencies.Version.scala

Global / onChangedBuildSource := ReloadOnSourceChanges

addCommandAlias("fmt", "all functional-project/scalafmtSbt functional-project/scalafmtAll")
addCommandAlias("fmtCheck", "all functional-project/scalafmtSbtCheck functional-project/scalafmtCheckAll")
addCommandAlias("check", "; scalafmtSbtCheck; scalafmtCheckAll")
addCommandAlias("publishKind", "fmt; clean; test; docker; kind; ")

// TODO: This shouldn't have DockerPlugin or KindPlugin.
//   We should move those to only the sub-modules when
//   we use sub-modules.
lazy val `functional-project` = (project in file("."))
  .enablePlugins(DockerPlugin, ScalafmtPlugin, KindPlugin)
  .settings(Settings.commonSettings)
  .settings(Settings.commonUberDockerSettings)
  .settings(Settings.commonKindSettings)
  .settings(organization := "org.cmoran")
  .settings(moduleName := "functional-project")
  .settings(name := "functional-project")
  .settings(libraryDependencies ++= Settings.basicDependencies ++ Seq(Dependencies.nscalatime))