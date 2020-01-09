package com.example.quizofy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import kotlinx.android.synthetic.main.activity_quiz_category1.*


class QuizCategory1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category1)
        startTime.setOnClickListener {
            object : CountDownTimer(30000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    textView3.setText("Time: 00:" + millisUntilFinished / 1000)
                    startTime.visibility=View.INVISIBLE
                }

                override fun onFinish() {
                    textView3.setText("Time Over!")
                    next1.setOnClickListener {
                        resetTimerforNextQuestion1()
                    }

                }
            }.start()
        }
    }

    fun resetTimerforNextQuestion1(){
        object : CountDownTimer(30000,1000){
            override fun onTick(milisUntilFinished: Long) {
                textView3.setText("Time:00:" + milisUntilFinished/1000)
                next1.visibility=View.INVISIBLE

            }

            override fun onFinish() {
                textView3.setText("Time Over!")
                next1.visibility=View.VISIBLE
            }
        }.start()
    }
}