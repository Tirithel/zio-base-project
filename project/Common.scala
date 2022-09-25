import sbt._

object Common {
  val resolvers = Seq("Artima Maven Repository" at "https://repo.artima.com/releases")

  val scalaVersion     = "2.13.8"
  val scalaTestVersion = "3.2.12"
  val scalaMockVersion = "5.2.0"
  val zioVersion       = "2.0.2"
}
