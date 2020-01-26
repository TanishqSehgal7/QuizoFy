package com.example.quizofy

import android.content.Intent
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class splashActivity : AppCompatActivity() {
    private val DelaySplash: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)

        Handler().postDelayed(
            {
                val intent=Intent(this,Main2ActivityIntroScreen::class.java)
                startActivity(intent)
                finish()
            }
        ,DelaySplash)
    }
}