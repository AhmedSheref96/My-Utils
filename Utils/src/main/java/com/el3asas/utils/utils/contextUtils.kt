package com.el3asas.utils.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

fun getActivity(context: Context?): Activity? {
    if (context == null) {
        return null
    } else if (context is ContextWrapper) {
        return if (context is Activity) {
            context
        } else {
            getActivity(context.baseContext)
        }
    }
    return null
}