import sbt._

object Dependencies {
  // scalatest
  val scalatic  = "org.scalactic" %% "scalactic" % Version.scalatest
  val scalatest = "org.scalatest" %% "scalatest" % Version.scalatest % Test

  // scalamock
  val scalamock = "org.scalamock" %% "scalamock" % Version.scalamock % Test

  // zio
  val zio        = "dev.zio"  %% "zio"              % Version.zio
  val zioCats    = ("dev.zio" %% "zio-interop-cats" % Version.zioCats).excludeAll(ExclusionRule("org.typelevel"))
  val zioStreams = "dev.zio"  %% "zio-streams"      % Version.zio
  val zioTest    = "dev.zio"  %% "zio-test"         % Version.zio % Test
  val zioTestSbt = "dev.zio"  %% "zio-test-sbt"     % Version.zio % Test

  // time
  val nscalatime = "com.github.nscala-time" %% "nscala-time" % Version.nscalaTime

  // logger
  val slf4j = "org.slf4j" % "slf4j-simple" % Version.slf4j

  object Version {
    val scala      = "2.13.8"
    val scalatest  = "3.2.12"
    val scalamock  = "5.2.0"
    val zio        = "2.0.2"
    val zioCats    = "3.3.0"
    val nscalaTime = "2.32.0"
    val slf4j      = "2.0.3"
  }

}
