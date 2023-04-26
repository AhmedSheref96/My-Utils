package com.el3asas.sample

import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import coil.imageLoader
import com.el3asas.sample.databinding.ActivityMainBinding
import com.el3asas.utils.utils.*
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.apply {
            lifecycleOwner = this@MainActivity
            executePendingBindings()
        }

        val button = findViewById<Button>(R.id.btn)

        val editText = findViewById<TextInputLayout>(R.id.editLayout)
        button.setOnClickListener {
            /*
            * ensure that override of resources
            * primaryColor defined in library is blue
            * primaryColor defined in app is red
            * */
            customSnackBar(it, "", com.el3asas.utils.R.drawable.ic_outline_error_outline_24) {}
            editText.notValidEditText("معلش", editText.isEmpty(), true, true, true)
        }

        val adapter = CustomAdapter(
            this, R.layout.menu, mutableListOf(
                Item(
                    "aaaa", R.drawable.ic_baseline_search_24, false
                )
            )
        )

        val menu = findViewById<TextInputLayout>(R.id.menu)
        val autoCompleteTextView = menu.editText as? AutoCompleteTextView

        autoCompleteTextView?.setOnItemClickListener { _, _, i, l ->

        }

        lifecycleScope.launch {

            getData(
                {
                    safeCall { Response.Success("") }
                },
                onSuccess = {},
                onError = { s, d ->

                },
            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        applicationContext.imageLoader.memoryCache?.clear()
    }
}