package com.el3asas.utils.base

import android.view.View
import androidx.lifecycle.ViewModel
import com.el3asas.utils.utils.clearNavigateStack
import kotlinx.coroutines.flow.MutableStateFlow

open class BaseViewModel : ViewModel() {
    val isLoading = MutableStateFlow(true)
    fun onBack(v: View) {
        clearNavigateStack(v, null)
    }
}