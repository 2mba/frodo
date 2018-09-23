package presentation

import domain.dto.DiceThrowResultDto
import domain.usecase.IUseCaseFactory
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler
import io.reactivex.rxkotlin.subscribeBy
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import org.tumba.frodo.domain.core.DevelopmentCard
import org.tumba.frodo.domain.core.SightCard

class GameViewModel(
    val players: List<String>
) : KoinComponent {

    var storeObservableList: ObservableList<DevelopmentCard> = FXCollections.observableArrayList()
    var playerStates: ObservableList<PlayerStateViewModel> = FXCollections.observableArrayList()
    var diceThrowResult: SimpleObjectProperty<DiceThrowResultDto?> = SimpleObjectProperty(null)

    var selectedStoreCards: SimpleObjectProperty<List<DevelopmentCard>> = SimpleObjectProperty(listOf())

    private val useCaseFactory: IUseCaseFactory by inject()

    fun start() {
        useCaseFactory
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
        useCaseFactory
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
        useCaseFactory
            .createBuyCardUseCase(card)
            .execute()
            .observeOn(JavaFxScheduler.platform())
            .subscribeBy()
    }

    private fun getState() {
        useCaseFactory
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