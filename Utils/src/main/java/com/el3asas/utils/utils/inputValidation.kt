package com.el3asas.utils.utils

fun isInputNotValid(
    conditionsToFail: List<Boolean>,
    errorList: List<String>,
    actionsList: List<(String) -> Unit>,
    reverseActionsList: List<() -> Unit>,
    globalAction: ((String) -> Unit)
): Boolean {
    for ((index, item) in conditionsToFail.withIndex()) {
        if (item) {
            if (index < actionsList.size) actionsList[index](errorList[index])
            globalAction(errorList[index])
            return true
        } else {
            if (reverseActionsList.size > index) reverseActionsList[index]()
        }
    }
    return false
}


fun isInputNotValid(
    conditionsToFail: List<Boolean>,
    errorList: List<String?>? = null,
    actionsList: List<(String?) -> Unit>? = null,
    reverseActionsList: List<() -> Unit>? = null,
    globalError: String? = null,
    globalAction: ((String) -> Unit)? = null
): Boolean {
    for ((index, item) in conditionsToFail.withIndex()) {
        if (item) {
            if (index < (actionsList?.size ?: -1)) actionsList?.get(index)
                ?.let { it(errorList?.get(index)) }
            globalAction?.let { errorAction ->
                val error = errorList?.get(index)

                if (error != null) {
                    (errorAction(error))
                } else if (globalError != null) {
                    errorAction(globalError)
                }
            }
            return true
        } else {
            if ((reverseActionsList?.size ?: -1) > index) reverseActionsList?.get(index)
                ?.let { it() }
        }
    }
    return false
}