package com.el3asas.utils.utils

import timber.log.Timber

class MyTimberTree(private val myTag: String) : Timber.DebugTree() {

    override fun d(message: String?, vararg args: Any?) {
        val mMsg = "$myTag $message"
        super.d(mMsg, *args)
    }
}