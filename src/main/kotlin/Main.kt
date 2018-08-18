package org.tumba.frodo

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.stage.Stage
import presentation.GameView



fun main(args: Array<String>) {
    println("Hello World")
    Application.launch(FrodoApplication::class.java)
}

class FrodoApplication: Application() {

    override fun start(primaryStage: Stage) {
        GameView.start(primaryStage)
    }
}