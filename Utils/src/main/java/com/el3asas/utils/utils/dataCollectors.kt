package com.el3asas.utils.utils

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow

class DataCollector<T>(
    private inline val onSuccess: ((T) -> Unit)? = null,
    private inline val onError: ((String) -> Unit)? = null,
    private inline val onLoading: ((Boolean) -> Unit)? = null
) : FlowCollector<Response<T>> {
    override suspend fun emit(value: Response<T>) {
        when (value) {
            is Response.Success -> {
                onLoading?.let { it(false) }
                value.data?.let { onSuccess?.let { it1 -> it1(it) } }
            }
            is Response.Error -> {
                onLoading?.let { it(false) }
                value.message?.let { onError?.let { it1 -> it1(it) } }
            }
            is Response.Loading -> {
                onLoading?.let { it(true) }
            }
        }
    }
}

fun <T> getData(
    value: Response<T>,
    onSuccess: ((T) -> Unit)? = null,
    onError: ((String) -> Unit)? = null,
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
            value.message?.let { onError?.let { it1 -> it1(it) } }
        }
        else -> {
            isLoading?.value = true
        }
    }
}

suspend fun <T> getData(
    dataResource: suspend () -> Response<T>,
    onSuccess: ((T) -> Unit)? = null,
    onError: ((String) -> Unit)? = null,
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
            value.message?.let { onError?.let { it1 -> it1(it) } }
        }
        else -> {
            isLoading.forEach { it?.value = true }
        }
    }
}

suspend fun getManyData(
    dataResource: List<suspend () -> Response<Any>>,
    onSuccess: List<(Any) -> Unit?>?,
    onError: List<(Any) -> Unit?>?,
    vararg isLoading: MutableStateFlow<Boolean>?
) {
    isLoading.forEach { it?.value = true }
    dataResource.forEachIndexed { index, item ->
        getData(
            dataResource = item,
            onError = {
                if (onError != null && onError.size > index) onError[index]
            },
            onSuccess = {
                if (onSuccess != null && onSuccess.size > index) onSuccess[index]
            },
            isLoading = isLoading,
        )
    }
}