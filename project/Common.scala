import java.time._
import java.time.format.DateTimeFormatter

import sbt._

import scala.sys.process._

object Common {
  val resolvers = Seq("Artima Maven Repository" at "https://repo.artima.com/releases")

  val currentCommitHash = ("git rev-parse --short HEAD" !! ProcessLogger(_ => ())).trim
  val timestamp         = LocalDateTime.now().format(DateTimeFormatter.ofPattern(" yyyyMMddHHmmss")).trim

  val dockerBaseImage     = "amazoncorretto:11"
  val localDockerImageTag = s"$timestamp-$currentCommitHash-LOCAL".trim
}
