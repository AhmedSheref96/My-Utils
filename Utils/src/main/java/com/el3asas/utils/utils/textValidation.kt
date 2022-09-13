package com.el3asas.utils.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.MutableLiveData
import com.chaos.view.PinView
import com.el3asas.utils.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.regex.Pattern

fun showErrorEditText(editText: TextInputEditText, errorId: Int) {
    editText.requestFocus()
    editText.error = editText.context.getString(errorId)
}

fun showErrorEditText(editText: PinView, errorId: Int) {
    editText.requestFocus()
    editText.error = editText.context.getString(errorId)
}

fun isNotEqual(password: String, confirmPassword: String): Boolean {
    return password != confirmPassword
}

fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isPasswordValid(password: String): Boolean {
    val regex = ("^(?=.*[a-z])(?=."
            + "*[A-Z])(?=.*\\d)"
            + "(?=.*[-+_!@#$%^&*., ?]).+$")

    // Compile the ReGex
    val p: Pattern = Pattern.compile(regex)

    // Find match between given string
    // & regular expression
    return p.matcher(password).matches()
}

fun isPhoneValid(phone: String): Boolean {
    return android.util.Patterns.PHONE.matcher(phone).matches()
}

fun shake(v: View) {
    val shake: Animation = AnimationUtils.loadAnimation(v.context, R.anim.shake)
    v.startAnimation(shake)
}

fun isInputEmpty(input: String?): Boolean {
    return input == null || input.trim().isEmpty()
}

fun isPhoneValid(phone: String, length: Int, startKey: String): Boolean {
    return android.util.Patterns.PHONE.matcher(phone)
        .matches() && phone.length == length && phone.startsWith(startKey)
}

fun <T : Any?> MutableStateFlow<T>.isNotValidValue(
    vararg notValidConditions: Boolean = booleanArrayOf(
        this.value == null,
        when (val value = this.value) {
            is Int -> value == 0
            is String -> value.isEmpty()
            else -> value.toString() == "null"
        }
    )
) = notValidConditions.all { it }

fun <T : Any?> MutableLiveData<T>.isNotValidValue(
    vararg notValidConditions: Boolean = booleanArrayOf(
        this.value == null,
        when (val value = this.value) {
            is Int -> value == 0
            is String -> value.isEmpty()
            else -> value.toString() == "null"
        }
    )
) = notValidConditions.all { it }