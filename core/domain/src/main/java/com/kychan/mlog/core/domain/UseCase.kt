package com.kychan.mlog.core.domain

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit

abstract class UseCase<in P, out R> {
    operator fun invoke(
        param: P,
        timeoutMs: Long = defaultTimeoutMs,
    ): Flow<R> = flow {
        withTimeout(timeoutMs){
            emit(doWork(param))
        }
    }

    suspend fun executeSync(params: P) = doWork(params)

    protected abstract suspend fun doWork(params: P): R

    companion object {
        private val defaultTimeoutMs = TimeUnit.MINUTES.toMillis(3)
    }
}

abstract class ObserveUseCase<in P, out R> {
    operator fun invoke(parameters: P): Flow<R> = createObservable(parameters)

    protected abstract fun createObservable(params: P): Flow<R>
}