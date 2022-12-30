package com.kychan.core.domain

import com.kychan.mlog.core.common.result.Result
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit

abstract class UseCase<in P, out R> {
    operator fun invoke(
        param: P,
        timeoutMs: Long = defaultTimeoutMs,
    ): Flow<Result<R>> = flow {
        try {
            withTimeout(timeoutMs){
                emit(Result.Loading)
                emit(Result.Success(doWork(param)))
            }
        } catch (e: Throwable) {
            emit(Result.Error(e))
        }
    }

    suspend fun executeSync(params: P) = doWork(params)

    protected abstract suspend fun doWork(params: P): R

    companion object {
        private val defaultTimeoutMs = TimeUnit.MINUTES.toMillis(3)
    }
}

abstract class ObserveUseCase<in P, out R> {
    // 아래에서 flatMapLatest를 사용하지만 호출(invoke)이 일시 중단(suspending)되지 않으므로 이상적으로는 버퍼 = 0입니다.
    // 즉, flatMapLatest가 기존 흐름을 취소하는 동안에는 일시 중단할 수 없습니다.
    // 버퍼 1은 tryEmit()을 사용하고 대신 값을 버퍼링할 수 있음을 의미하며, 결과는 거의 동일합니다.
    private val paramState = MutableSharedFlow<P>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    val flow: Flow<R> = paramState
        .distinctUntilChanged()
        .flatMapLatest { createObservable(it) }
        .distinctUntilChanged()

    operator fun invoke(params: P) {
        paramState.tryEmit(params)
    }

    protected abstract fun createObservable(params: P): Flow<R>
}