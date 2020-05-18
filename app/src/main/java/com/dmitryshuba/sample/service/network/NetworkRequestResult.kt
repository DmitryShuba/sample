package com.dmitryshuba.sample.service.network

sealed class NetworkRequestResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : NetworkRequestResult<T>()

    data class Failure(val errorCode: Int?, val errorMessage: String) : NetworkRequestResult<Nothing>()

    object SuccessWithoutBody : NetworkRequestResult<Nothing>()
}