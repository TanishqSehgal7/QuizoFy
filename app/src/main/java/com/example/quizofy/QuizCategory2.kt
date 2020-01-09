package com.example.quizofy

import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import kotlinx.android.synthetic.main.activity_quiz_category1.*
import kotlinx.android.synthetic.main.activity_quiz_category1.textView3
import kotlinx.android.synthetic.main.activity_quiz_category2.*

class QuizCategory2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category2)
        startTime2.setOnClickListener {
            object : CountDownTimer(30000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    textView3.setText("Time:00:" + millisUntilFinished / 1000)
                    startTime2.visibility=View.INVISIBLE
                }

                override fun onFinish() {
                    textView3.setText("Time Over!")
                    next2.setOnClickListener {
                        resetTimerforNextQuestion2()
                    }
                }
            }.start()
        }
    }

    fun resetTimerforNextQuestion2(){
        object : CountDownTimer(30000,1000){
            override fun onTick(milisUntilFinished: Long) {
                textView3.setText("Time:00:" + milisUntilFinished/1000)
                next2.visibility=View.INVISIBLE

            }

            override fun onFinish() {
                textView3.setText("Time Over!")
                next2.visibility=View.VISIBLE
            }
        }.start()
    }
}
