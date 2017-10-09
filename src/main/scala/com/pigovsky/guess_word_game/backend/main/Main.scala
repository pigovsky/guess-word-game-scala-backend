package com.pigovsky.guess_word_game.backend.main
import com.pigovsky.guess_word_game.backend.modules.Injector
import com.pigovsky.guess_word_game.backend.services.endpoint.EndPoint

object Main extends App {
  println("""Starting "guess word" server""")
  Injector().getInstance(classOf[EndPoint]).run()
}
