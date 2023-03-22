package com.el3asas.utils.utils

import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.el3asas.utils.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun clickableSnackBar(
    v: View,
    message: String,
    onDismiss: (() -> Unit)? = null,
    iconId: Int? = R.drawable.ic_baseline_error_outline_24,
    btnTitle: String = "دخول",
    clickableBtnListener: (View) -> Unit
) {
    try {

        val sb: Snackbar = Snackbar.make(
            v, message, Snackbar.LENGTH_LONG
        )

        sb.setAction(btnTitle) {
            clickableBtnListener(it)
        }

        val sbView = sb.view

        sbView.setBackgroundColor(ContextCompat.getColor(v.context, R.color.primaryColor))

        val textView =
            sbView.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView

        textView.setTextColor(ContextCompat.getColor(v.context, R.color.white))

        iconId?.let { textView.setCompoundDrawablesWithIntrinsicBounds(it, 0, 0, 0) }
        textView.gravity = Gravity.CENTER

        textView.compoundDrawablePadding = 12
        onDismiss?.let { dismiss ->
            sb.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    dismiss()
                }
            })
        }
        sb.anchorView = v
        sb.show()
    } catch (ignored: Exception) {
    }
}