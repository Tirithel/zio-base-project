import sbt._
import Settings._

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := Dependencies.Version.scala

Global / onChangedBuildSource := ReloadOnSourceChanges

addCommandAlias("fmt", "all root/scalafmtSbt root/scalafmtAll")
addCommandAlias("fmtCheck", "all root/scalafmtSbtCheck root/scalafmtCheckAll")
addCommandAlias("check", "; scalafmtSbtCheck; scalafmtCheckAll")
addCommandAlias("publishKind", s"fmt; clean; test; docker; kind; ")

lazy val root = (project in file("."))
  .enablePlugins(DockerPlugin, ScalafmtPlugin, KindPlugin)
  .settings(Settings.commonSettings)
  .settings(Settings.commonUberDockerSettings)
  .settings(Settings.commonKindSettings)
  .settings(organization := "org.cmoran")
  .settings(moduleName := "functional-project")
  .settings(name := "functional-project")
  .settings(libraryDependencies ++= Settings.serviceLayerDependencies)
