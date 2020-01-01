package com.example.quizofy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main2_intro_screen.*
import kotlinx.android.synthetic.main.activity_quiz_category1.*

class Main2ActivityIntroScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2_intro_screen)

        val button:Button=findViewById(R.id.intro)

        button.setOnClickListener {
            startIntent()
        }

    }

    fun startIntent(){
        val intent=Intent(this,MainActivity::class.java)
        startActivity(intent)

    }
}