package presentation

import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.StackPane
import javafx.stage.Stage


class GameView(
    private val application: Application,
    private val primaryStage: Stage
) : IGameView {

    private lateinit var presenter: GamePresenter

    fun start() {
        createScene()
        presenter = GamePresenter(listOf(), this)
        presenter.start()
    }

    override fun showError(msg: String) {
    }

    override fun onStartGame() {
    }

    private fun createScene() {
        primaryStage.title = "Hello World!"
        val btn = Button()
        btn.text = "Say 'Hello World'"
        btn.onAction = EventHandler<ActionEvent> { println("Hello World!") }

        val root = StackPane()
        root.children.add(btn)
        primaryStage.scene = Scene(root, 300.0, 250.0)
        primaryStage.show()
    }
}