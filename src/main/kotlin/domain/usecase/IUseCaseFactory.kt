package domain.usecase

interface IUseCaseFactory {

    fun createStartGameUseCase(): IStartGameUseCase
}