package com.el3asas.utils.utils

import com.el3asas.utils.R
import com.google.android.material.textfield.TextInputLayout

fun Char.repeat(count: Int): String = this.toString().repeat(count)

fun TextInputLayout.notValidEditText(
    errorMsg: String?,
    vararg notValidConditions: Boolean?
): Boolean {
    val isAllNotValid = notValidConditions.all { it == true }
    return if (isAllNotValid) {
        this.error = errorMsg ?: this.context.getString(R.string.notValid)
        true
    } else false
}
