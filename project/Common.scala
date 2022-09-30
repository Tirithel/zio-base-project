import sbt._

import sbtassembly.AssemblyKeys.assembly
import sbtdocker.{ Dockerfile, ImageName }
import sbtdocker.DockerKeys._

object Common {
  val resolvers = Seq("Artima Maven Repository" at "https://repo.artima.com/releases")

  val scalaVersion      = "2.13.8"
  val scalaTestVersion  = "3.2.12"
  val scalaMockVersion  = "5.2.0"
  val zioVersion        = "2.0.2"
  val zioCatsVersion    = "3.3.0"
  val nscalaTimeVersion = "2.32.0"

  val dockerBaseImage     = "amazoncorretto:11"
  val dockerImageName     = "functionalproject"
  val localDockerImageTag = "1.0.0-LOCAL" // this cant be dynamically generated

  val dockerSettings = Seq(
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
    docker / imageNames := Seq(ImageName(None, None, dockerImageName, Some(localDockerImageTag))),
  )

}
