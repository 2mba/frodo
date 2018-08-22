package presentation

import domain.dto.PlayerStateDto
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler
import io.reactivex.rxkotlin.subscribeBy
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.tumba.frodo.domain.core.DevelopmentCard
import org.tumba.frodo.domain.usecase.UseCaseFactory

class GameViewModel(
    val players: List<String>,
    private val view: IGameView
) {

    var storeObservableList: ObservableList<DevelopmentCard> = FXCollections.observableArrayList()
    var playerStates: ObservableList<PlayerStateDto> = FXCollections.observableArrayList(PlayerStateDto(0, listOf(), listOf(), 50))

    fun start() {
        UseCaseFactory
            .instance
            .createStartGameUseCase(players)
            .execute()
            .observeOn(JavaFxScheduler.platform())
            .subscribeBy(
                onError = { err ->
                    view.showError(err.message.orEmpty())
                    err.printStackTrace()
                },
                onComplete = {
                    view.onStartGame()
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
                    playerStates.addAll(state.playerStates)

                    println("Receive " + state)
                }
            )
    }
}