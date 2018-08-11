package org.tumba.frodo.domain.usecase

import domain.usecase.IUseCaseFactory

class UseCaseFactory: IUseCaseFactory {

    fun createStartGameUseCase(): StartGameUseCase = StartGameUseCase()

    companion object {

        val instance by lazy { UseCaseFactory() }
    }
}