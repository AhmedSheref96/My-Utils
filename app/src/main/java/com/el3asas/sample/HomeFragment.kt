package com.el3asas.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.el3asas.sample.databinding.HomeFragmentBinding
import com.el3asas.utils.binding.FragmentBinding

class HomeFragment(override val bindingInflater: (LayoutInflater) -> ViewBinding = HomeFragmentBinding::inflate) :
    FragmentBinding<HomeFragmentBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title
    }

}