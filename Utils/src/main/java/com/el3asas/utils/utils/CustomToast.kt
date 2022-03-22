package com.el3asas.utils.utils

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.el3asas.utils.R

fun showToast(
    context: Context,
    stringId: Int,
    iconId: Int,
    gravity: Int
) {

    val toast = Toast.makeText(context, context.getString(stringId), Toast.LENGTH_LONG)

    val view = toast.view

    view?.setBackgroundColor(ContextCompat.getColor(context, R.color.primaryColor))

    val text = view?.findViewById(android.R.id.message) as TextView

    text.setTextColor(Color.WHITE)
    text.setCompoundDrawablesWithIntrinsicBounds(iconId, 0, 0, 0)

    text.gravity = Gravity.CENTER

    text.compoundDrawablePadding = 12

    toast.setGravity(gravity, 0, 0)
    toast.show()

}