package com.el3asas.utils.utils

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow

class DataCollector<T>(
    private inline val onSuccess: ((T) -> Unit),
    private inline val onError: ((Throwable) -> Unit),
    private inline val onLoading: ((Boolean) -> Unit)? = null
) : FlowCollector<Result<T>> {
    override suspend fun emit(value: Result<T>) {
        onLoading?.let { it(true) }
        value.fold(onSuccess, onError)
        onLoading?.let { it(false) }
    }
}

fun <T> getData(
    value: Response<T>,
    onSuccess: ((T) -> Unit)? = null,
    onError: ((String, T?) -> Unit)? = null,
    isLoading: MutableStateFlow<Boolean>? = null
) {
    isLoading?.value = true
    when (value) {
        is Response.Success -> {
            isLoading?.value = false
            value.data?.let { onSuccess?.let { it1 -> it1(it) } }
        }

        is Response.Error -> {
            isLoading?.value = false
            value.message?.let { onError?.let { it1 -> it1(it, value.data) } }
        }

        else -> {
            isLoading?.value = true
        }
    }
}

suspend fun <T> getData(
    dataResource: suspend () -> Response<T>,
    onSuccess: ((T) -> Unit)? = null,
    onError: ((String, T?) -> Unit)? = null,
    vararg isLoading: MutableStateFlow<Boolean>?
) {
    isLoading.forEach { it?.value = true }
    when (val value = dataResource()) {
        is Response.Success -> {
            isLoading.forEach { it?.value = false }
            value.data?.let { onSuccess?.let { it1 -> it1(it) } }
        }

        is Response.Error -> {
            isLoading.forEach { it?.value = false }
            value.message?.let { onError?.let { it1 -> it1(it, value.data) } }
        }

        else -> {
            isLoading.forEach { it?.value = true }
        }
    }
}

suspend fun <T : Any> getManyData(
    dataResource: List<suspend () -> Response<T>>,
    onSuccess: Map<Int, ((T) -> Unit)?>? = null,
    onError: Map<Int, ((String, T?) -> Unit)?>? = null,
    vararg isLoading: MutableStateFlow<Boolean>?
) {
    isLoading.forEach { it?.value = true }
    dataResource.forEachIndexed { index, item ->
        getData(
            dataResource = item,
            onError = onError?.get(index),
            onSuccess = onSuccess?.get(index),
            isLoading = isLoading,
        )
    }
}