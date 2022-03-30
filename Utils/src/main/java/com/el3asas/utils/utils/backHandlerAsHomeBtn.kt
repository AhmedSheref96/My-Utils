package com.el3asas.utils.utils

import android.content.Intent
import androidx.activity.addCallback
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkBuilder

fun backHandlerAsHomeBtn(act: FragmentActivity) {
    act.onBackPressedDispatcher.addCallback {
        val intent = Intent()
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_HOME)
        act.startActivity(intent)
    }
}

fun backHandlerAsHomeBtn(navController: NavController){
 //   NavDeepLinkBuilder()
}