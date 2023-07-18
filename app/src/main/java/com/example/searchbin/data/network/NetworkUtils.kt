package com.example.searchbin.data.network

import com.google.gson.GsonBuilder
import retrofit2.HttpException
import timber.log.Timber
import java.net.UnknownHostException

suspend fun <T> safeCall(
    block: suspend () -> T
): ResponseStates<T> = runCatching {
    ResponseStates.Success(block())
}.getOrElse {
    Timber.e(it)
    checkThrowable(it)
}

sealed interface ResponseStates<out T> {
    data class Success<T>(val data: T) : ResponseStates<T>
    data class Error(val exception: Throwable) : ErrorState
}

sealed interface ErrorState : ResponseStates<Nothing> {
    data class GenericError(val throwable: Throwable) : ErrorState
    data class ServerError(
        val code: Int? = null,
        val message: String? = null,
        val response: NetworkErrorResponse? = null
    ) : ErrorState

    object ConnectionError : ErrorState
    object SuccessNoResult : ErrorState
}

private fun checkThrowable(throwable: Throwable): ResponseStates<Nothing> =
    when (throwable) {
        is HttpException -> ErrorState.SuccessNoResult
        is UnknownHostException -> ErrorState.ConnectionError
        else -> ErrorState.GenericError(throwable)
    }

data class NetworkErrorResponse(
    val code: String,
    val reason: String
)

/** Use in classic way to handle with HTTP exception */
private fun parseHttpException(exception: HttpException): ResponseStates<Nothing> {
    return runCatching {
        val response = exception.response()?.let {
            GsonBuilder()
                .create()
                .fromJson(it.body().toString(), NetworkErrorResponse::class.java)
        }
        if (response != null) {
            ErrorState.ServerError(
                exception.code(),
                exception.message(),
                response
            )
        } else {
            ErrorState.ServerError()
        }
    }.getOrElse {
        ErrorState.GenericError(it)
    }
}

