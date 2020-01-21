package com.example.quizofy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        closeBtn.setOnClickListener {
            exitTointroScreen()
        }

    }

    fun exitTointroScreen(){
        val intent =Intent(this,Main2ActivityIntroScreen::class.java)
    }
}
