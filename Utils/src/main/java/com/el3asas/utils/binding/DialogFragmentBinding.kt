package com.el3asas.utils.binding

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

open class DialogFragmentBinding : DialogFragment() {
    protected inline fun <reified T : ViewDataBinding> binding(
        inflater: LayoutInflater,
        layout: Int,
        container: ViewGroup?
    ): T = DataBindingUtil.inflate(inflater, layout, container, false)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
       // dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        return dialog
    }
}