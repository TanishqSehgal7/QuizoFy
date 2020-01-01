package com.example.quizofy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_MESSAGE = "com.example.quizofy.MESSEGE"


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
   fun startQuiz1(view: View){
       val button=findViewById<Button>(R.id.c1)
       val intent=Intent(this,QuizCategory1::class.java)
       startActivity(intent)
   }
    fun startQuiz2(view: View){
        val button=findViewById<Button>(R.id.c2)
        val intent=Intent(this,QuizCategory2::class.java)
        startActivity(intent)
    }
    fun startQuiz3(view: View){
        val button=findViewById<Button>(R.id.c3)
        val intent=Intent(this,QuizCategory3::class.java)
        startActivity(intent)
    }
    fun startQuiz4(view: View){
        val button=findViewById<Button>(R.id.c4)
        val intent=Intent(this,QuizCategory4::class.java)
        startActivity(intent)
    }
    fun startQuiz5(view: View){
        val button=findViewById<Button>(R.id.c5)
        val intent=Intent(this,QuizCategory5::class.java)
        startActivity(intent)
    }
    fun startQuiz6(view: View){
        val button=findViewById<Button>(R.id.c6)
        val intent=Intent(this,QuizCategory6::class.java)
        startActivity(intent)
    }
    fun startQuiz7(view: View){
        val button=findViewById<Button>(R.id.c7)
        val intent=Intent(this,QuizCategory7::class.java)
        startActivity(intent)
    }
    fun startQuiz8(view: View){
        val button=findViewById<Button>(R.id.c8)
        val intent=Intent(this,QuizCategory8::class.java)
        startActivity(intent)
    }
    fun startQuiz9(view: View){
        val button=findViewById<Button>(R.id.c9)
        val intent=Intent(this,QuizCategory9::class.java)
        startActivity(intent)
    }
    fun startQuiz10(view: View){
        val button=findViewById<Button>(R.id.c10)
        val intent=Intent(this,QuizCategory10::class.java)
        startActivity(intent)
    }

}
