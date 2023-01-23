package com.example.searchbin.utils

import com.google.gson.GsonBuilder
import retrofit2.HttpException
import timber.log.Timber
import java.net.UnknownHostException

suspend fun <T> safeApiCall(
    block: suspend () -> T
): NetworkResponseStates<T> = runCatching {
    NetworkResponseStates.Success(block())
}.getOrElse {
    Timber.e(it)
    checkThrowable(it)
}


sealed interface NetworkResponseStates<out T> {
    data class Success<T>(val data: T) : NetworkResponseStates<T>
    data class Error(val exception: Throwable) : ErrorState
}

sealed interface ErrorState : NetworkResponseStates<Nothing> {
    data class GenericError(val throwable: Throwable) : ErrorState
    data class ServerError(
        val code: Int? = null,
        val message: String? = null,
        val response: NetworkErrorResponse? = null
    ) : ErrorState

    object ConnectionError : ErrorState
}

private fun checkThrowable(throwable: Throwable): NetworkResponseStates<Nothing> = when (throwable) {
    is HttpException -> parseHttpException(throwable)
    is UnknownHostException -> ErrorState.ConnectionError
    else -> ErrorState.GenericError(throwable)
}

private fun parseHttpException(exception: HttpException): NetworkResponseStates<Nothing> {
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

data class NetworkErrorResponse(
    val code: String,
    val reason: String
)

