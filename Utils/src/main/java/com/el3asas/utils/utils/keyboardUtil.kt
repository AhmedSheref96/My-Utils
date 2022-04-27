package com.el3asas.utils.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun hideKeyboard(view: View) {
    val inputMethodManager =
        view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

    if (inputMethodManager.isActive) {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun hideKeyboard(activity: Activity) {
    val view = activity.currentFocus
    if (view != null) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun View.showKeyboard() {
    val inputMethodManager =
        this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}