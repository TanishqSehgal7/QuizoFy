package com.example.quizofy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main2_intro_screen.*


class Main2ActivityIntroScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2_intro_screen)



    }

    fun SelectCategory(view: View){
//        val button = findViewById<Button>(R.id.intro)
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }


}
