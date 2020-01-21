package com.example.quizofy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import kotlinx.android.synthetic.main.activity_quiz_category1.*
import kotlinx.android.synthetic.main.activity_quiz_category1.textView3
import kotlinx.android.synthetic.main.activity_quiz_category10.*
import kotlinx.android.synthetic.main.activity_quiz_category2.*
import kotlinx.android.synthetic.main.activity_quiz_category6.*

class QuizCategory6 : AppCompatActivity() {
lateinit var timeCount:CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category6)

        startTime6.setOnClickListener {
            startTime6.visibility=View.INVISIBLE
            startClicked6()
        }
    }

    fun startClicked6() {

        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1+(milisUntilFinished / 1000)))

                if (radioButton61.isChecked||radioButton62.isChecked||radioButton63.isChecked||radioButton64.isChecked) {
                    optionupdate()
                }
                else {
                    next6.setOnClickListener {
                        next6.visibility=View.INVISIBLE
                        resetTimer()
                        optionupdate()
                        next6.visibility=View.VISIBLE
                    }

                }
            }
            override fun onFinish() {
                textView3.text = "Time Over!!"

                next6.setOnClickListener {
                    optionupdate()

                }
            }
        }.start()
    }

    fun resetTimer(){
        timeCount.start()
    }


    fun optionupdate() {
        if (radioButton61.isChecked || radioButton62.isChecked || radioButton63.isChecked || radioButton64.isChecked) {
            radioButton61.isEnabled = false
            radioButton62.isEnabled = false
            radioButton63.isEnabled = false
            radioButton64.isEnabled = false
            next6.setOnClickListener {
                resetTimer()
                resetoptions()
                radioButton61.isEnabled = true
                radioButton62.isEnabled = true
                radioButton63.isEnabled = true
                radioButton64.isEnabled = true
            }

        } else {
            next6.setOnClickListener {
                resetTimer()
            }
        }
    }

    fun resetoptions(){
        if(radioButton61.isChecked){
            radioButton61.isChecked=false
        }
        else if (radioButton62.isChecked){
            radioButton62.isChecked=false
        }
        else if (radioButton63.isChecked){
            radioButton63.isChecked=false
        }
        else {
            radioButton64.isChecked=false
        }
    }
}