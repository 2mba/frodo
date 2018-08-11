package domain.usecase

import io.reactivex.Completable
import org.tumba.frodo.domain.usecase.IUseCase

interface IStartGameUseCase : IUseCase<Completable>