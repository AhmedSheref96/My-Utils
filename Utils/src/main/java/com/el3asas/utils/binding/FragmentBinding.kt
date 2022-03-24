package com.el3asas.utils.binding

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

open class FragmentBinding : Fragment() {

    protected inline fun <reified T : ViewDataBinding> binding(
        inflater: LayoutInflater,
        layout: Int,
        container: ViewGroup?
    ): T = DataBindingUtil.inflate(inflater, layout, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }
}