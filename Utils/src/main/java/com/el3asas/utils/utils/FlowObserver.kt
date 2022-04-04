package com.el3asas.utils.utils

import kotlinx.coroutines.flow.FlowCollector

class FlowObserver<T>(
    private inline val onError: ((String) -> Unit)? = null,
    private inline val onLoading: (() -> Unit)? = null,
    private inline val onSuccess: (T) -> Unit
) : FlowCollector<Result<T>> {
    override suspend fun emit(value: Result<T>) {
        when (value) {
            is Result.Success -> {
                value.data?.let { onSuccess(it) }
            }
            is Result.Error -> {
                value.message?.let { onError?.let { it1 -> it1(it) } }
            }
            is Result.Loading -> {
                onLoading?.let { it() }
            }
        }
    }

}