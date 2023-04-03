package com.el3asas.utils.utils

sealed class Response<out T>(
    val data: T? = null,
    val message: String? = null,
    val code: Int? = null,
) {
    class Success<T>(data: T) : Response<T>(data)

    class Error<T>(message: String, data: T? = null, code: Int? = null) :
        Response<T>(data, message, code)

    class Loading<T>(data: T? = null) : Response<T>(data)

}