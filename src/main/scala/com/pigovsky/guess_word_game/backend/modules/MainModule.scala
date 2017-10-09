package com.pigovsky.guess_word_game.backend.modules
import com.google.inject.AbstractModule
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.pigovsky.guess_word_game.backend.services.{UserService, UserServiceInMemoryImpl}
import com.pigovsky.guess_word_game.backend.services.endpoint.{EndPoint, EndPointImpl}

class MainModule extends AbstractModule {

  override def configure(): Unit = {
    implicit val system = ActorSystem("guess-word-actors")
    bind(classOf[ActorSystem]).toInstance(system)
    bind(classOf[ActorMaterializer]).toInstance(ActorMaterializer())
    bind(classOf[EndPoint]).to(classOf[EndPointImpl])
    bind(classOf[UserService]).to(classOf[UserServiceInMemoryImpl])
  }

}
