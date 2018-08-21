package org.tumba.frodo.domain.usecase

import domain.usecase.IStartGameUseCase
import io.reactivex.Completable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import org.tumba.frodo.domain.core.Player
import org.tumba.frodo.domain.game.*

class StartGameUseCase(
    private val playerNames: List<String>,
    private val gameHolder: GameHolder
) : IStartGameUseCase {

    override fun execute(): Completable {
        return run {
            val gameEventSubject = BehaviorSubject.create<Game>()
            gameHolder.game = GameFactory().create(
                players = playerNames.mapIndexed { index, name -> Player(index, name) },
                gameStateChangeCallback = createGameStateChangeCallback(gameEventSubject)
            )
            gameHolder.gameEventSubject = gameEventSubject
        }
    }

    private fun createGameStateChangeCallback(gameEventSubject: Subject<Game>): IGameStateChangeCallback {
        return object : IGameStateChangeCallback {

            override fun onGameStateChanged(vararg change: GameStateChange) {
                gameHolder.game?.let { gameEventSubject.onNext(it) }
            }

        }
    }
}