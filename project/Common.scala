import sbt._

object Common {
  val resolvers = Seq("Artima Maven Repository" at "https://repo.artima.com/releases")

  val dockerBaseImage     = "amazoncorretto:11"
  val localDockerImageTag = "1.0.0-LOCAL" // this cant be dynamically generated
}
