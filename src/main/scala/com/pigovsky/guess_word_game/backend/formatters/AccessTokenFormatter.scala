package com.pigovsky.guess_word_game.backend.formatters

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.pigovsky.guess_word_game.backend.models.AccessToken
import spray.json.{DefaultJsonProtocol, JsValue}

trait AccessTokenFormatter extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val accessTokenFormat = jsonFormat1(AccessToken)
}

object AccessTokenFormatterExtensions extends AccessTokenFormatter {
  implicit class AccessTokenFormatterExtension(val data: AccessToken) extends AnyVal {
    def toJson: JsValue =
      accessTokenFormat.write(data)
  }
}