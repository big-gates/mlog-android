package com.kychan.mlog.domain.usecase

import com.kychan.mlog.util.Result
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class CoroutineUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(parameters: P): Result<R>{
        // Moving all use case's executions to the injected dispatcher
        // In production code, this is usually the Default dispatcher (background thread)
        // In tests, this becomes a TestCoroutineDispatcher
        return try{
            withContext(coroutineDispatcher){
                execute(parameters).let {
                    Result.Success(it)
                }
            }
        } catch (e: Exception){
            Logger.e(e.toString())
            Result.Error(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract fun execute(parameters: P): R
}