package com.dmitryshuba.sample.service.network

import kotlinx.coroutines.Deferred
import retrofit2.Response
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException

@Suppress("TooGenericExceptionCaught", "ComplexMethod")
suspend fun <T : Any> Deferred<Response<T>>.performSafeApiCallResult(): NetworkRequestResult<T> {
    return try {
        val result = this.await()
        if (result.isSuccessful) {
            if (result.body() != null)
                NetworkRequestResult.Success(result.body()!!)
            else
                NetworkRequestResult.SuccessWithoutBody
        } else
            handleErrorBody(result)
    } catch (exception: Exception) {
        val code = if (exception::class.java == ConnectException::class.java ||
            exception::class.java == SocketTimeoutException::class.java
        )
            HttpURLConnection.HTTP_UNAVAILABLE
        else null
        NetworkRequestResult.Failure(
            code,
            "Something went wrong"
        )
    }
}

private fun <T : Any> handleErrorBody(response: Response<T>): NetworkRequestResult.Failure {
    val code = response.code()
    var errorMessage = response.message()
    try {
        if (response.errorBody() != null) {
            errorMessage = response.errorBody().toString()
        }
    } finally {
        return NetworkRequestResult.Failure(
            code,
            errorMessage
        )
    }
}
