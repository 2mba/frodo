package presentation

import domain.dto.PlayerStateDto
import javafx.beans.binding.Bindings
import javafx.beans.property.ReadOnlyIntegerWrapper
import javafx.beans.property.ReadOnlyStringWrapper
import javafx.collections.ListChangeListener
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.layout.HBox
import javafx.stage.Stage
import org.tumba.frodo.FrodoApplication
import org.tumba.frodo.domain.core.DevelopmentCard
import java.util.concurrent.Callable


class GameView {

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
    @FXML
    lateinit var diceThrowResult: Label

    private lateinit var viewModel: GameViewModel
    private var playerViews: MutableList<PlayerView> = mutableListOf()

    fun initialize() {

        println("Controller working")
        viewModel = GameViewModel(listOf("Pavel", "Baira"))

        viewModel.playerStates.addListener(ListChangeListener { change -> onPlayerStateChanged() })

        val binding = Bindings.createStringBinding(Callable {
            "${viewModel.diceThrowResult.value?.sum ?: "-"}"
        }, viewModel.diceThrowResult)
        diceThrowResult.textProperty().bind(binding)

        storeView.items = viewModel.storeObservableList
        storeColumnName.setCellValueFactory { cellData -> ReadOnlyStringWrapper(cellData.value.cardName) }
        storeColumnPrice.setCellValueFactory { cellData -> ReadOnlyIntegerWrapper(cellData.value.cost) }
        storeColumnType.setCellValueFactory { cellData -> ReadOnlyStringWrapper(cellData.value.productionType.name) }

        storeView.selectionModel.selectedItems.addListener(ListChangeListener { change ->
            viewModel.selectedStoreCards.set(change.list)
        })

        viewModel.start()
    }

    fun onClick(actionEvent: ActionEvent) {
        viewModel.throwDice()
    }

    fun onClickBuyCard(actionEvent: ActionEvent) {
        viewModel.buyCard()
    }

    private fun onPlayerStateChanged() {
        if (viewModel.playerStates.isEmpty()) return

        if (viewModel.playerStates.size != playersBox.children.size) {
            initPlayerStatesView(viewModel.playerStates.size)
        }

        playerViews.forEachIndexed { index, playerView ->
            updatePlayerView(playerView, viewModel.playerStates[index])
        }

    }

    private fun initPlayerStatesView(numberOfPlayer: Int) {
        playersBox.children.clear()
        playerViews.clear()

        for (i in 0 until numberOfPlayer) {
            val playerView = PlayerView.create()
            playerViews.add(playerView)
            playersBox.children.add(playerView.root)
        }
    }

    private fun updatePlayerView(playerView: PlayerView, playerStateDto: PlayerStateDto) {
        playerView.name.text = viewModel.players.getOrElse(playerStateDto.playerNumber, { "Unknown" })
        playerView.coins.text = playerStateDto.coins.toString()
        playerView.cardsObservableList.apply {
            clear()
            addAll(playerStateDto.cards.map { it.cardName })
        }
    }

    companion object {

        fun start(primaryStage: Stage) {
            val loader = FXMLLoader(FrodoApplication::class.java.classLoader.getResource("GameView.fxml"))
            val root = loader.load<Parent>()
            val controller = loader.getController<GameView>()
            primaryStage.title = "Hello World"
            primaryStage.scene = Scene(root)
            primaryStage.show()
        }
    }
}