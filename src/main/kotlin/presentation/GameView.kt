package presentation

import domain.dto.GameStateDto
import javafx.beans.property.ReadOnlyIntegerWrapper
import javafx.beans.property.ReadOnlyStringWrapper
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.stage.Stage
import org.tumba.frodo.FrodoApplication
import org.tumba.frodo.domain.core.DevelopmentCard


class GameView: IGameView {

    @FXML
    lateinit var storeView: TableView<DevelopmentCard>
    @FXML
    lateinit var storeColumnName: TableColumn<DevelopmentCard, String>
    @FXML
    lateinit var storeColumnPrice: TableColumn<DevelopmentCard, Number>
    @FXML
    lateinit var storeColumnType: TableColumn<DevelopmentCard, String>

    private lateinit var viewModel: GameViewModel

    fun initialize() {
        println("Controller working")
        viewModel = GameViewModel(listOf("Pavel", "Baira"), this)
        viewModel.start()

        storeView.items = viewModel.storeObservableList
        storeColumnName.setCellValueFactory { cellData  -> ReadOnlyStringWrapper(cellData.value.cardName) }
        storeColumnPrice.setCellValueFactory { cellData  -> ReadOnlyIntegerWrapper(cellData.value.cost) }
        storeColumnType.setCellValueFactory { cellData  -> ReadOnlyStringWrapper(cellData.value.productionType.name) }
    }

    override fun showError(msg: String) {
    }

    override fun onStartGame() {

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
}