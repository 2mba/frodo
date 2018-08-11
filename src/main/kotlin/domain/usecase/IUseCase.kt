package org.tumba.frodo.domain.usecase

import io.reactivex.Completable

interface IUseCase<T> {

    fun execute(): T

    fun run(block: () -> Unit): Completable = Completable.fromAction(block)
}

