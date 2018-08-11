package domain.usecase

interface IUseCaseFactory {

    fun createStartGameUseCase(playerNames: List<String>): IStartGameUseCase

    fun createGetGameStateUseCase(): IGetGameStateUseCase
}