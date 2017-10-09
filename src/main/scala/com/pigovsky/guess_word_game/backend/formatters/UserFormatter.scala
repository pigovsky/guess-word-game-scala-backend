package com.pigovsky.guess_word_game.backend.formatters

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.pigovsky.guess_word_game.backend.models.User
import spray.json.{DefaultJsonProtocol, JsValue}

trait UserFormatter extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val userFormat = jsonFormat2(User)
}

object UserFormatterExtensions extends UserFormatter {
  implicit class UserFormatterExtension(val user: User) extends AnyVal {
    def toJson: JsValue =
      userFormat.write(user)
  }
}