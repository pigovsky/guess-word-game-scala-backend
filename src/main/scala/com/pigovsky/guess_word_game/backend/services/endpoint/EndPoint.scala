package com.pigovsky.guess_word_game.backend.services.endpoint
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.google.inject.Inject
import com.pigovsky.guess_word_game.backend.config.Config
import com.pigovsky.guess_word_game.backend.extensions.AllAkkaHttpExtensions.HttpResponseOrExtension
import com.pigovsky.guess_word_game.backend.formatters.{AccessTokenFormatter, UserFormatter, UserFormatterExtensions}
import com.pigovsky.guess_word_game.backend.models.{AccessToken, User}
import com.pigovsky.guess_word_game.backend.services.UserService
import UserFormatterExtensions._
import com.pigovsky.guess_word_game.backend.formatters.AccessTokenFormatterExtensions._

trait EndPoint {
  def run(): Unit
}

class EndPointImpl @Inject() (
  implicit val system: ActorSystem,
  implicit val materializer: ActorMaterializer,
  userService: UserService
) extends EndPoint with UserFormatter {
  implicit val ec = system.dispatcher

  val route =
      path("users") {
        post {
          entity(as[User]) {
            user =>
              complete(userService.add(user).toAsyncResponse(_.toJson))
          }
        } ~
        get {
          parameters('accessToken) { accessToken =>
            complete(userService.get(AccessToken(accessToken)).toAsyncResponse(_.toJson))
          }
        }
      } ~
      path ("login") {
        post {
          entity(as[User]) {
            user => complete(userService.login(user).toAsyncResponse(_.toJson))
          }
        }
      }

  override def run(): Unit = {
    val bindingFuture = Http().bindAndHandle(route, Config.serverHost, Config.serverPort)

    println(s"Server online at http://${Config.serverHost}:${Config.serverPort}")

    sys.addShutdownHook({
      println("Stopping the game server...")
      bindingFuture
        .flatMap(_.unbind()) // trigger unbinding from the port
        .onComplete(_ => system.terminate()) // and shutdown when done
    })
  }
}

