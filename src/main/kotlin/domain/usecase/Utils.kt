package domain.usecase

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

fun useCaseScheduler(): Scheduler = Schedulers.newThread()