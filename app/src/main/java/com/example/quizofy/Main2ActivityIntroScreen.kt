package com.example.quizofy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2_intro_screen.*
import kotlinx.android.synthetic.main.activity_quiz_category1.*

class Main2ActivityIntroScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2_intro_screen)

        Toast.makeText(this,"Tap the button to browse categories",Toast.LENGTH_SHORT).show()

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