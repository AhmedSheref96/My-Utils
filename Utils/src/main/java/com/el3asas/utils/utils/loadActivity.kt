package com.el3asas.utils.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.internal.ContextUtils
import java.util.*

@SuppressLint("RestrictedApi")
fun loadActivity(context: Context, cls: Class<*>) {
    val intent = Intent(context, cls)
    startActivity(context, intent, null)
    val act = ContextUtils.getActivity(context)
    act?.finish()
}
fun setLocale(activity: Activity, lang: String) {
    val myLocale = Locale(lang)
    Locale.setDefault(myLocale)
    val res = activity.resources
    val dm = res.displayMetrics
    val conf = Configuration()
    conf.locale = myLocale
    res.updateConfiguration(conf, dm)
    activity.onConfigurationChanged(conf)
}