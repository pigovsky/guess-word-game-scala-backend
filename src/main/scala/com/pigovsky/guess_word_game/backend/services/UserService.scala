package com.pigovsky.guess_word_game.backend.services

import java.util.UUID

import com.pigovsky.guess_word_game.backend.models.{AccessToken, User}
import com.pigovsky.guess_word_game.backend.models.errors.UserServiceErrors._
import scalty.types._

import scala.collection.mutable

trait UserService {
  def add(user: User): Or[User]
  def get(accessToken: AccessToken): Or[User]
  def login(user: User): Or[AccessToken]
}

class UserServiceInMemoryImpl extends UserService {
  private val users = mutable.Set[User]()
  private val accessTokens = mutable.Map[User, AccessToken]()

  override def add(user: User): Or[User] = {
    if (users.map(_.login) contains user.login) {
      UserWithSuchLoginAlreadyExists.toErrorOr
    } else {
      users += user
      hidePassword(user).toOr
    }
  }

  override def login(user: User): Or[AccessToken] = {
    if (users contains user) {
      val accessToken = AccessToken(UUID.randomUUID().toString)
      accessTokens(user) = accessToken
      accessToken.toOr
    } else {
      InvalidLoginOrPassword.toErrorOr
    }
  }

  override def get(searchedAccessToken: AccessToken): Or[User] =
    accessTokens.find {
      case (_, accessToken) => accessToken == searchedAccessToken
    } map {
      case (user, _) => user
    } match {
      case Some(user) => hidePassword(user).toOr
      case _ => InvalidAccessToken.toErrorOr
    }

  private def hidePassword(user: User) = {
    user.copy(password = "secret ))")
  }
}
