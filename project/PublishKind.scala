import sbt._

import scala.sys.process._

object PublishKind {

  def publishKindLocal = Command.args("publishKindLocal", "") { (state, args) =>
    val func = "publishKindLocal"
    args.size match {
      case 1 =>
        val clusterName = args.head
        info(state, func)(s"Reading values from ./project/Common.scala...")
        val dockerImageName = Common.dockerImageName
        val dockerImageTag  = Common.localDockerImageTag
        val dockerImage     = s"$dockerImageName:$dockerImageTag"

        info(state, func)(s"Docker Image(s) [$dockerImage]")
        info(state, func)(s"Cluster Name [$clusterName]")

        // run the command and hide the output
        val cmd = s"kind load docker-image $dockerImage --name $clusterName"
        val rc  = cmd ! ProcessLogger(_ => ())

        rc match {
          case 0 =>
            success(state, func)(s"Published $dockerImage to $clusterName!")
            state
          case _ =>
            error(state, func)(s"Execution of `$cmd` failed, check to see if cluster `$clusterName` exists.")
            state.fail
        }
      case _ =>
        error(state, func)(s"Invalid number of args, one expected: [$args]")
        state.fail
    }
  }

  private def info(state: State, func: String)(message: String)    = state.log.info(s"($func) $message")
  private def error(state: State, func: String)(message: String)   = state.log.error(s"($func) $message")
  private def success(state: State, func: String)(message: String) = state.log.success(s"($func) $message")

}
