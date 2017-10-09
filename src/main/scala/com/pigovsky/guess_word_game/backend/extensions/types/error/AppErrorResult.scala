package com.pigovsky.guess_word_game.backend.extensions.types.error

import scalty.types.AppError
import spray.json.{JsObject, JsString, JsValue}

/**
  * Created by stefaniv on 18.05.17.
  */
trait AppErrorResult extends AppError {
  def description: String
  def toJson: JsValue           = JsObject(Map("message" -> JsString(description)))
  override def toString: String = s"${this.getClass.getCanonicalName}: $description"

}
