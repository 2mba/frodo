package org.tumba.frodo

import javafx.application.Application
import javafx.stage.Stage
import org.tumba.frodo.di.KoinDi
import presentation.GameView


fun main(args: Array<String>) {
    println("Hello World")
    initDi()
    Application.launch(FrodoApplication::class.java)
}

private fun initDi() = KoinDi().start()

class FrodoApplication: Application() {

    override fun start(primaryStage: Stage) {
        GameView.start(primaryStage)
    }
}