package com.el3asas.utils.utils

fun <T> safeCall(action: () -> Response<T>): Response<T> {
    return try {
        action()
    } catch(e: Exception) {
        Response.Error(e.message ?: "An unknown error occurred")
    }
}