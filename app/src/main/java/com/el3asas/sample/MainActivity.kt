package com.el3asas.sample

import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.el3asas.utils.utils.customSnackBar
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.btn)

        button.setOnClickListener {
            /*
            * ensure that override of resources
            * primaryColor defined in library is blue
            * primaryColor defined in app is red
            * */
            customSnackBar(it, "", com.el3asas.utils.R.drawable.ic_outline_error_outline_24) {}
        }

        val adapter = CustomAdapter(
            this, R.layout.menu, mutableListOf(
                Item(
                    "aaaa",
                    R.drawable.ic_baseline_search_24, false
                )
            )
        )

        val menu = findViewById<TextInputLayout>(R.id.menu).editText as? AutoCompleteTextView
        menu?.setAdapter(adapter)

        menu?.setOnItemClickListener { _, _, i, l ->

        }

    }
}