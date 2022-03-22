package com.el3asas.utils.utils

import android.content.Context
import android.net.ConnectivityManager

const val WIFI_CONNECTED_STATUS = 1
const val MOBILE_NETWORK_STATUS = 2
const val NO_INTERNET_CONNECTION = 0

internal object NetworkUtil {
    fun getConnectivityStatusString(context: Context): Int? {
        var status: Int? = null
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork != null) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                status = WIFI_CONNECTED_STATUS
                return status
            } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                status = MOBILE_NETWORK_STATUS
                return status
            }
        } else {
            status = NO_INTERNET_CONNECTION
            return status
        }
        return status
    }
}