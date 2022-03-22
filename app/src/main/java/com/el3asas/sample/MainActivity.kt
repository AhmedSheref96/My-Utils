package com.el3asas.sample

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.el3asas.utils.utils.customSnackBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        createNotificationChannel("fff","dddd",application)
        val button = findViewById<Button>(R.id.btn)
        button.setOnClickListener {
            customSnackBar(it, "", com.el3asas.utils.R.drawable.ic_outline_error_outline_24) {}
        }

    }
}