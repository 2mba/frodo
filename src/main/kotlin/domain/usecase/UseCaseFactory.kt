package org.tumba.frodo.domain.usecase

import domain.usecase.GetGameStateUseCase
import domain.usecase.IGetGameStateUseCase
import domain.usecase.IUseCaseFactory
import org.tumba.frodo.domain.game.GameHolder

class UseCaseFactory: IUseCaseFactory {

    override fun createStartGameUseCase(playerNames: List<String>): StartGameUseCase  {
        return StartGameUseCase(playerNames, GameHolder.instance)
    }

    override fun createGetGameStateUseCase(): IGetGameStateUseCase {
        return GetGameStateUseCase()
    }

    companion object {

        val instance by lazy { UseCaseFactory() }
    }
}