package com.el3asas.utils.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseViewModel : ViewModel() {
    val isLoading = MutableLiveData(true)
    val isError = MutableLiveData(false)

}