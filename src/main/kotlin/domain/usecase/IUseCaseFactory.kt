package domain.usecase

import org.tumba.frodo.domain.core.DevelopmentCard

interface IUseCaseFactory {

    fun createStartGameUseCase(playerNames: List<String>): IStartGameUseCase

    fun createGetGameStateUseCase(): IGetGameStateUseCase

    fun createThrowDiceUseCase(): IThrowDiceUseCase

    fun createBuyCardUseCase(card: DevelopmentCard): IBuyCardCase

    fun createEndTurnUseCase(): IEndTurnCase
}