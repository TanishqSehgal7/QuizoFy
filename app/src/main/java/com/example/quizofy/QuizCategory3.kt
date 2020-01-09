package com.example.quizofy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import kotlinx.android.synthetic.main.activity_quiz_category1.*
import kotlinx.android.synthetic.main.activity_quiz_category1.textView3
import kotlinx.android.synthetic.main.activity_quiz_category3.*

class QuizCategory3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category3)
        startTime3.setOnClickListener {
            object : CountDownTimer(30000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    textView3.setText("Time:00:" + millisUntilFinished / 1000)
                    startTime3.visibility=View.INVISIBLE
                }

                override fun onFinish() {
                    textView3.setText("Time Over!")
                    next3.setOnClickListener {
                        resetTimerforNextQuestion3()
                    }
                }
            }.start()
        }

    }

    fun resetTimerforNextQuestion3(){
        object : CountDownTimer(30000,1000){
            override fun onTick(milisUntilFinished: Long) {
                textView3.setText("Time:00:" + milisUntilFinished/1000)
                next3.visibility=View.INVISIBLE

            }

            override fun onFinish() {
                textView3.setText("Time Over!")
                next3.visibility=View.VISIBLE
            }
        }.start()
    }
}
