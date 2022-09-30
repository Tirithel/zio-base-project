import sbt._
import sbt.Keys._

import scala.sys.process._

import org.scalafmt.sbt.ScalafmtPlugin.autoImport.scalafmtOnCompile

import sbtassembly.AssemblyKeys.assemblyJarName
import sbtassembly.AssemblyPlugin.autoImport.assembly
import sbtdocker.DockerKeys._
import sbtdocker.DockerPlugin.autoImport.ImageName
import sbtdocker.Dockerfile
import sbtkind.KindKeys._
import Dependencies._

object Settings {

  val commonSettings = Seq(
    scalaVersion        := Version.scala,
    logLevel            := Level.Info,
    scalafmtOnCompile   := true,
    testFrameworks      := Seq(new TestFramework("zio.test.sbt.ZTestFramework")),
    Global / cancelable := true, // https://github.com/sbt/sbt/issues/2274
    Global / fork       := true, // ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

    assembly / assemblyJarName := project.id + "-uber.jar",
    resolvers ++= Common.resolvers,
  )

  val commonUberDockerSettings = Seq(
    docker / imageName := {
      val organisation = Option(Keys.organization.value).filter(_.nonEmpty)
      val name         = Keys.normalizedName.value
      new ImageName(namespace = organisation, repository = name, tag = Some(Common.localDockerImageTag))
    },
    docker / imageNames := {
      Seq((docker / imageName).value)
    },
    docker / dockerfile := {
      // The assembly task generates a fat JAR file
      val artifact: File     = assembly.value
      val artifactTargetPath = s"/app/${artifact.name}"

      new Dockerfile {
        from(Common.dockerBaseImage)
        add(artifact, artifactTargetPath)
        entryPoint("java", "-jar", artifactTargetPath)
      }
    },
  )

  val commonKindSettings = Seq(
    kind / clusterName      := ("whoami" !!).strip,
    kind / dockerImageNames := (docker / imageNames).value.map(_.toString()),
  )

  val dataLayerDependencies    = List(zio, zioCats)
  val serviceLayerDependencies = List(zioCats, zioTest, zioTestSbt) // TODO: add slf4j and dedupe the assembly
  val backendDependencies      = List()
}
