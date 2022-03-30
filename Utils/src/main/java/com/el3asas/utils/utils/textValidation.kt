package com.el3asas.utils.utils

import com.chaos.view.PinView
import com.google.android.material.textfield.TextInputEditText
import java.util.regex.Pattern

fun showErrorEditText(editText: TextInputEditText, errorId: Int) {
    editText.requestFocus()
    editText.error = editText.context.getString(errorId)
}

fun showErrorEditText(editText: PinView, errorId: Int) {
    editText.requestFocus()
    editText.error = editText.context.getString(errorId)
}

fun isInputEmpty(input: String): Boolean {
    return input.isEmpty()
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