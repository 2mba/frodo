package presentation

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.TitledPane
import org.tumba.frodo.FrodoApplication

class PlayerView {

    @FXML
    lateinit var root: Parent
    @FXML
    lateinit var name: TitledPane
    @FXML
    lateinit var coins: Label
    @FXML
    lateinit var cards: ListView<String>

    val cardsObservableList: ObservableList<String> = FXCollections.observableArrayList()

    companion object {

        fun create(): PlayerView {
            val loader = FXMLLoader(FrodoApplication::class.java.classLoader.getResource("PlayerView.fxml"))
            val root = loader.load<Parent>()

            return loader.getController<PlayerView>().apply {
                cards.items = cardsObservableList
                this.root = root
            }
        }
    }
}