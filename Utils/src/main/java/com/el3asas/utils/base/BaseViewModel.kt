package com.el3asas.utils.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

open class BaseViewModel : ViewModel() {
    val isLoading = MutableStateFlow(true)
}