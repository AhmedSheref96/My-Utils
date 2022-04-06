package com.el3asas.utils.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest


class NetworkMonitoringUtil : NetworkCallback() {
    private var mNetworkRequest: NetworkRequest? = null
    private var mConnectivityManager: ConnectivityManager? = null

    fun NetworkMonitoringUtil(context: Context) {
        mNetworkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
        mConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
    }

    fun registerNetworkCallbackEvents() {
        mConnectivityManager!!.registerNetworkCallback(mNetworkRequest!!, this)
    }
}