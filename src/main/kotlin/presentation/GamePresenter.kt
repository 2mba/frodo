package presentation

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
            .subscribeBy(
                onError = { err -> view.showError(err.message.orEmpty()) },
                onComplete = { view.onStartGame() }
            )
    }
}