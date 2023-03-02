package com.el3asas.utils.utils

suspend fun <T> safeCall(action: suspend () -> Response<T>): Response<T> {
    return try {
        action()
    } catch (e: Exception) {
        Response.Error(e.message ?: "An unknown error occurred")
    }
}

suspend fun <T> safeCalls(vararg actions: suspend () -> Response<T>): List<Response<T>> {
    return actions.map {
        try {
            it()
        } catch (e: Exception) {
            Response.Error(e.message.toString())
        }
    }
}