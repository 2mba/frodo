package org.tumba.frodo.di

import domain.usecase.IUseCaseFactory
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.startKoin
import org.tumba.frodo.domain.usecase.UseCaseFactory

class KoinDi {

    private val mainModule = module {

        single { UseCaseFactory() as IUseCaseFactory }
    }

    fun start() {
        startKoin(listOf(mainModule))
    }
}