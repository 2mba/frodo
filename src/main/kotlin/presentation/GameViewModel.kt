package presentation

import domain.dto.DiceThrowResultDto
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler
import io.reactivex.rxkotlin.subscribeBy
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.tumba.frodo.domain.core.DevelopmentCard
import org.tumba.frodo.domain.core.SightCard
import org.tumba.frodo.domain.usecase.UseCaseFactory

class GameViewModel(
    val players: List<String>
) {

    var storeObservableList: ObservableList<DevelopmentCard> = FXCollections.observableArrayList()
    var playerStates: ObservableList<PlayerStateViewModel> = FXCollections.observableArrayList()
    var diceThrowResult: SimpleObjectProperty<DiceThrowResultDto?> = SimpleObjectProperty(null)

    var selectedStoreCards: SimpleObjectProperty<List<DevelopmentCard>> = SimpleObjectProperty(listOf())

    fun start() {
        UseCaseFactory
            .instance
            .createStartGameUseCase(players)
            .execute()
            .observeOn(JavaFxScheduler.platform())
            .subscribeBy(
                onError = { err ->
                    //view.showError(err.message.orEmpty())
                    err.printStackTrace()
                },
                onComplete = {
                    //view.onStartGame()
                    getState()
                }
            )

    }

    fun throwDice() {
        UseCaseFactory
            .instance
            .createThrowDiceUseCase()
            .execute()
            .observeOn(JavaFxScheduler.platform())
            .subscribeBy()
    }

    fun buyCard() {
        selectedStoreCards.value.firstOrNull()?.let { card ->
            buyCard(card)
        }
    }

    private fun buyCard(card: DevelopmentCard) {
        UseCaseFactory
            .instance
            .createBuyCardUseCase(card)
            .execute()
            .observeOn(JavaFxScheduler.platform())
            .subscribeBy()
    }

    private fun getState() {              
        UseCaseFactory
            .instance
            .createGetGameStateUseCase()
            .execute()
            .observeOn(JavaFxScheduler.platform())
            .subscribeBy(
                onNext = { state ->
                    storeObservableList.clear()
                    storeObservableList.addAll(state.storeDto.cards)

                    playerStates.clear()
                    playerStates.addAll(state.playerStates.map { playerState ->
                        PlayerStateViewModel(
                            playerId = playerState.playerId,
                            cards = playerState.cards,
                            sightCards = playerState.sightCards,
                            coins = playerState.coins,
                            isPlayerTurn = playerState.playerId == state.turnOfPlayer
                        )
                    })

                    diceThrowResult.value = state.diceThrowResult

                    println("Receive $state")
                }
            )
    }

    class PlayerStateViewModel(
        val playerId: Int,
        val cards: List<DevelopmentCard>,
        val sightCards: List<SightCard>,
        val coins: Int,
        val isPlayerTurn: Boolean
    )
}