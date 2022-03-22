package com.el3asas.utils.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val isLoading = MutableLiveData(true)
    val isError = MutableLiveData(false)
}