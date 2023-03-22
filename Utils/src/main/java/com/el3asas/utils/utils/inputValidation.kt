package com.el3asas.utils.utils

fun isInputNotValid(
    conditionsToFail: List<Boolean>,
    errorList: List<String>,
    actionsList: List<(String) -> Unit>,
    reverseActionsList: List<() -> Unit>,
    globalAction: ((String) -> Unit)? = null
): Boolean {
    for ((index, item) in conditionsToFail.withIndex()) {
        if (item) {
            if (index < actionsList.size)
                actionsList[index](errorList[index])
            globalAction?.let { it(errorList[index]) }
            return true
        } else {
            if (reverseActionsList.size > index)
                reverseActionsList[index]()
        }
    }
    return false
}