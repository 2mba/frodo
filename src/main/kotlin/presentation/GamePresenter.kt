package presentation

import io.reactivex.rxjavafx.schedulers.JavaFxScheduler
import io.reactivex.rxkotlin.subscribeBy
import org.tumba.frodo.domain.usecase.UseCaseFactory

class GamePresenter(
    private val players: List<String>,
    private val view: IGameView
) {

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
                onNext = { state -> view.updateGameState(state) }
            )
    }
}