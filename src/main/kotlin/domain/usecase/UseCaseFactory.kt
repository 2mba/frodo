package org.tumba.frodo.domain.usecase

import domain.usecase.*
import org.tumba.frodo.domain.game.GameHolder

class UseCaseFactory: IUseCaseFactory {

    override fun createStartGameUseCase(playerNames: List<String>): StartGameUseCase  {
        return StartGameUseCase(playerNames, GameHolder.instance)
    }

    override fun createGetGameStateUseCase(): IGetGameStateUseCase {
        return GetGameStateUseCase(GameHolder.instance.game!!)
    }

    override fun createThrowDiceUseCase(): IThrowDiceUseCase {
        return ThrowDiceUseCase(GameHolder.instance.game!!)
    }

    companion object {

        val instance by lazy { UseCaseFactory() }
    }
}