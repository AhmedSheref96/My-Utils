package com.el3asas.utils.utils

inline fun <T> safeCall(action: () -> Result<T>): Result<T> {
    return try {
        action()
    } catch(e: Exception) {
        Result.Error(e.message ?: "An unknown error occurred")
    }
}