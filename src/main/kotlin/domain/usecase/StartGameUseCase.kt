package org.tumba.frodo.domain.usecase

import domain.usecase.IStartGameUseCase
import io.reactivex.Completable
import org.tumba.frodo.domain.core.Player
import org.tumba.frodo.domain.game.GameFactory
import org.tumba.frodo.domain.game.GameHolder

class StartGameUseCase(
    private val playerNames: List<String>,
    private val gameHolder: GameHolder
) : IStartGameUseCase {

    override fun execute(): Completable {
        return run {
            gameHolder.game = GameFactory().create(
                players = playerNames.mapIndexed { index, name -> Player(index, name) }
            )
        }
    }
}