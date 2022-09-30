package org.cmoran

import scala.language.postfixOps

import zio._

object Main extends ZIOAppDefault {

  final case class Config(config: Config.SomeConfig)

  object Config {

    final case class SomeConfig(token: String)

    val live: TaskLayer[Config] = ZLayer {
      ZIO.from(new Config(SomeConfig("cool token")))
    }

  }

  override def run: RIO[Scope, Unit] = {
    val program = for {
      someService <- ZIO.service[SomeService]
      _           <- someService.doSomething.repeat(Schedule.spaced(5 seconds))
    } yield ()

    program
      .provideSome(
        Config.live,
        SomeService.live,
      )
  }

  trait SomeService {
    def doSomething: Task[Unit]
  }

  object SomeService {

    val live: URLayer[Config, SomeService] = ZLayer(ZIO.service[Config] map { cfg =>
      val someToken = cfg.config.token
      new SomeService {
        override def doSomething: Task[Unit] = ZIO.attempt {
          println(s"Effect with a $someToken from my config!")
        }.unit
      }
    })

  }

}
