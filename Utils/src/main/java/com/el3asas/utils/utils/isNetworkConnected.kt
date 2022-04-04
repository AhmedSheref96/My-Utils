package com.el3asas.utils.utils

import android.content.Context
import android.net.ConnectivityManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun isNetworkConnected(@ApplicationContext context: Context): Flow<Boolean> = flow {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.let {
            val activeNetwork = cm.activeNetworkInfo
            emit(activeNetwork != null && activeNetwork.isConnectedOrConnecting)
        }

        emit(false)
    }