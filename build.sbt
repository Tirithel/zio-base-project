ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.2"

Global / onChangedBuildSource := ReloadOnSourceChanges

scalafmtFilter := "diff-dirty"

addCommandAlias("fmt", "all root/scalafmtSbt root/scalafmtAll")
addCommandAlias("fmtCheck", "all root/scalafmtSbtCheck root/scalafmtCheckAll")
addCommandAlias("check", "; scalafmtSbtCheck; scalafmtCheckAll")

lazy val root = (project in file("."))
  .settings(
    name := "FunctionalProject"
  )
