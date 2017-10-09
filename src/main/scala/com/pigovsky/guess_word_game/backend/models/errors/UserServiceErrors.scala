package com.pigovsky.guess_word_game.backend.models.errors
import com.pigovsky.guess_word_game.backend.extensions.types.error.AppErrorResult
import scalty.types._

case class UserServiceError(description: String) extends AppErrorResult

object UserServiceErrors {
  val UserWithSuchLoginAlreadyExists = UserServiceError("user with such login already exists")
  val InvalidLoginOrPassword = UserServiceError("invalid login or password")
  val InvalidAccessToken = UserServiceError("invalid access token")
}
