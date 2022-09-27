ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := Common.scalaVersion

Global / onChangedBuildSource := ReloadOnSourceChanges

addCommandAlias("fmt", "all root/scalafmtSbt root/scalafmtAll")
addCommandAlias("fmtCheck", "all root/scalafmtSbtCheck root/scalafmtCheckAll")
addCommandAlias("check", "; scalafmtSbtCheck; scalafmtCheckAll")
addCommandAlias("publishKind", "fmt; clean; docker; ")

scalafmtOnCompile := true

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
      //time
      "com.github.nscala-time" %% "nscala-time" % Common.nscalaTimeVersion,
    ),
  )
  .settings(Common.dockerSettings)

//lazy val publishKindLocal = taskKey[String]("Publishes generated uber JAR to kind cluster.")
//
//docker / publishKindLocal := {
//  import scala.sys.process._
//
//  val user = ("whoami" !!).strip
//  val dockerImage = Common.dockerImageName + ":" + Common.localDockerImageTag
//
//  s"kind load docker-image $dockerImage --name kind-$user" !!
//}