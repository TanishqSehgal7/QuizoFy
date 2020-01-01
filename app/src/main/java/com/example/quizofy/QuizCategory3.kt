package com.example.quizofy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import kotlinx.android.synthetic.main.activity_quiz_category1.*

class QuizCategory3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category3)
        object : CountDownTimer(200000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textView3.setText("Time:" + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                textView3.setText("Time Over!")
            }
        }.start()
    }

    
}
