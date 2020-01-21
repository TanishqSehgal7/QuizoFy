package com.example.quizofy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import kotlinx.android.synthetic.main.activity_quiz_category1.*
import kotlinx.android.synthetic.main.activity_quiz_category1.textView3
import kotlinx.android.synthetic.main.activity_quiz_category10.*
import kotlinx.android.synthetic.main.activity_quiz_category2.*
import kotlinx.android.synthetic.main.activity_quiz_category5.*

class QuizCategory5 : AppCompatActivity() {
lateinit var timeCount:CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category5)

        startTime5.setOnClickListener {
            startTime5.visibility=View.INVISIBLE
            startClicked5()
        }

    }

    fun startClicked5() {

        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1+(milisUntilFinished / 1000)))

                if (radioButton51.isChecked||radioButton52.isChecked||radioButton53.isChecked||radioButton54.isChecked) {
                    optionupdate()
                }
                else {
                    next5.setOnClickListener {
                        next5.visibility=View.INVISIBLE
                        resetTimer()
                        optionupdate()
                        next5.visibility=View.VISIBLE
                    }

                }
            }
            override fun onFinish() {
                textView3.text = "Time Over!!"

                next5.setOnClickListener {
                    optionupdate()

                }
            }
        }.start()
    }

    fun resetTimer(){
        timeCount.start()
    }


    fun optionupdate() {
        if (radioButton51.isChecked || radioButton52.isChecked || radioButton53.isChecked || radioButton54.isChecked) {
            radioButton51.isEnabled = false
            radioButton52.isEnabled = false
            radioButton53.isEnabled = false
            radioButton54.isEnabled = false
            next5.setOnClickListener {
                resetTimer()
                resetoptions()
                radioButton51.isEnabled = true
                radioButton52.isEnabled = true
                radioButton53.isEnabled = true
                radioButton54.isEnabled = true
            }

        } else {
            next5.setOnClickListener {
                resetTimer()
            }
        }
    }

    fun resetoptions(){
        if(radioButton51.isChecked){
            radioButton51.isChecked=false
        }
        else if (radioButton52.isChecked){
            radioButton52.isChecked=false
        }
        else if (radioButton53.isChecked){
            radioButton53.isChecked=false
        }
        else {
            radioButton54.isChecked=false
        }
    }
}
