package com.el3asas.utils.utils

import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import com.el3asas.utils.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


fun customSnackBar(
    v: View,
    message: String,
    iconId: Int,
    onDismiss: () -> Unit
) {
    try {

        val sb: Snackbar = Snackbar.make(v, message, Snackbar.LENGTH_SHORT)

        val sbView = sb.view

        sbView.setBackgroundColor(getColor(v.context, R.color.primaryColor))

        val textView =
            sbView.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView

        textView.setTextColor(getColor(v.context, R.color.white))

        textView.setCompoundDrawablesWithIntrinsicBounds(iconId, 0, 0, 0)

        textView.gravity = Gravity.CENTER

        textView.compoundDrawablePadding = 12

        sb.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                onDismiss()
            }
        })
        sb.show()
    } catch (ignored: Exception) {
    }
}