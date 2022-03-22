package com.el3asas.utils.utils

import androidx.core.content.ContextCompat
import com.el3asas.utils.R
import com.google.android.material.textfield.TextInputEditText

fun Char.repeat(count: Int): String = this.toString().repeat(count)

fun TextInputEditText.notValidEditText() =
    if (this.text?.isEmpty() == true) {
        this.setError(
            this.context.getString(R.string.notValid),
            ContextCompat.getDrawable(this.context, R.drawable.ic_outline_error_outline_24)
        )
        true
    } else false
