ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := Common.scalaVersion

Global / onChangedBuildSource := ReloadOnSourceChanges

addCommandAlias("fmt", "all root/scalafmtSbt root/scalafmtAll")
addCommandAlias("fmtCheck", "all root/scalafmtSbtCheck root/scalafmtCheckAll")
addCommandAlias("check", "; scalafmtSbtCheck; scalafmtCheckAll")
addCommandAlias("publishKind", s"fmt; clean; test; docker; kind; ")

scalafmtOnCompile := true

lazy val root = (project in file("."))
  .enablePlugins(DockerPlugin, ScalafmtPlugin, KindPlugin)
  .settings(
    name := "FunctionalProject",
    assembly / assemblyJarName := project.id + "-uber.jar",
    kind / clusterName := {
      import scala.sys.process._
      ("whoami" !!).strip + ""
    },
    kind / dockerImageNames := (docker / imageNames).value.map(_.toString()),
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
      //time
      "com.github.nscala-time" %% "nscala-time" % Common.nscalaTimeVersion,
    ),
  )
  .settings(Common.dockerSettings)
