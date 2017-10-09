package com.pigovsky.guess_word_game.backend.modules

import com.google.inject.{Guice, Injector}

object Injector {
  lazy val instance = Guice.createInjector(new MainModule)

  def apply(): Injector =
    instance
}

