package com.sane4ek.zefirgg.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.sane4ek.zefirgg.R
import com.sane4ek.zefirgg.utils.WindowSettings

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WindowSettings().fullScreen(window)
    }
}