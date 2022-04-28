package com.el3asas.utils.utils

suspend fun <T> safeCall(action: suspend () -> Response<T>): Response<T> {
    return try {
        action()
    } catch(e: Exception) {
        Response.Error(e.message ?: "An unknown error occurred")
    }
}