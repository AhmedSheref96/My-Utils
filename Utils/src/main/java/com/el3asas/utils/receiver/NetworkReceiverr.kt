package com.el3asas.utils.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.el3asas.utils.utils.MOBILE_NETWORK_STATUS

import com.el3asas.utils.utils.NO_INTERNET_CONNECTION
import com.el3asas.utils.utils.NetworkUtil.getConnectivityStatusString
import com.el3asas.utils.utils.WIFI_CONNECTED_STATUS
/*
class NetworkReceiverr : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (getConnectivityStatusString(context)) {
            NO_INTERNET_CONNECTION -> {
                ConnectionStatus.onConnectionError.postValue(true)
            }
            WIFI_CONNECTED_STATUS -> {
                ConnectionStatus.onConnectionError.postValue(false)
            }
            MOBILE_NETWORK_STATUS -> {
                ConnectionStatus.onConnectionError.postValue(false)
            }
        }
    }
}*/