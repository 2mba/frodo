package presentation

import domain.dto.GameStateDto
import javafx.beans.property.ReadOnlyIntegerWrapper
import javafx.beans.property.ReadOnlyStringWrapper
import javafx.collections.ListChangeListener
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.layout.HBox
import javafx.stage.Stage
import org.tumba.frodo.FrodoApplication
import org.tumba.frodo.domain.core.DevelopmentCard


class GameView : IGameView {

    @FXML
    lateinit var storeView: TableView<DevelopmentCard>
    @FXML
    lateinit var storeColumnName: TableColumn<DevelopmentCard, String>
    @FXML
    lateinit var storeColumnPrice: TableColumn<DevelopmentCard, Number>
    @FXML
    lateinit var storeColumnType: TableColumn<DevelopmentCard, String>
    @FXML
    lateinit var playersBox: HBox

    private lateinit var viewModel: GameViewModel

    fun initialize() {

        println("Controller working")
        viewModel = GameViewModel(listOf("Pavel", "Baira"), this)

        viewModel.playerStates.addListener(ListChangeListener { change ->
            if (playersBox.children.isEmpty()) {
                change.list.forEach {
                    playersBox.children.add(Label("#"))
                }
            }
            onPlayerStateChanged()
        })

        storeView.items = viewModel.storeObservableList
        storeColumnName.setCellValueFactory { cellData -> ReadOnlyStringWrapper(cellData.value.cardName) }
        storeColumnPrice.setCellValueFactory { cellData -> ReadOnlyIntegerWrapper(cellData.value.cost) }
        storeColumnType.setCellValueFactory { cellData -> ReadOnlyStringWrapper(cellData.value.productionType.name) }

        viewModel.start()
    }

    override fun showError(msg: String) {
    }

    override fun onStartGame() {
    }

    private fun onPlayerStateChanged() {
        if (viewModel.playerStates.isEmpty()) return

        playersBox.children.forEachIndexed { idx, node ->
            val state = viewModel.playerStates[idx] ?: return@forEachIndexed
            (node as? Label?)?.apply {
                text = state.toString() + "   |    "
            }
        }
    }

    companion object {

        fun start(primaryStage: Stage) {
            val loader = FXMLLoader(FrodoApplication::class.java.classLoader.getResource("GameView.fxml"))
            val root = loader.load<Parent>()
            val controller = loader.getController<GameView>()
            primaryStage.title = "Hello World"
            primaryStage.scene = Scene(root, 300.0, 275.0)
            primaryStage.show()
        }
    }

    fun onClick(actionEvent: ActionEvent) {
        viewModel.throwDice()
    }
}