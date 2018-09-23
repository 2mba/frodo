package org.tumba.frodo.domain.usecase

import domain.usecase.*
import org.tumba.frodo.domain.core.DevelopmentCard
import org.tumba.frodo.domain.game.GameHolder

class UseCaseFactory: IUseCaseFactory {

    override fun createStartGameUseCase(playerNames: List<String>): StartGameUseCase  {
        return StartGameUseCase(playerNames, GameHolder.instance)
    }

    override fun createGetGameStateUseCase(): IGetGameStateUseCase {
        return GetGameStateUseCase(GameHolder.instance)
    }

    override fun createThrowDiceUseCase(): IThrowDiceUseCase {
        return ThrowDiceUseCase(GameHolder.instance.game ?: throw IllegalStateException("Game had not been started"))
    }

    override fun createBuyCardUseCase(card: DevelopmentCard): IBuyCardCase {
        return BuyCardUseCase(card, GameHolder.instance)
    }
}