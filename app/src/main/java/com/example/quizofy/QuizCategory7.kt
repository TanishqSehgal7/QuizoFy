package com.example.quizofy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import kotlinx.android.synthetic.main.activity_quiz_category1.*
import kotlinx.android.synthetic.main.activity_quiz_category1.textView3
import kotlinx.android.synthetic.main.activity_quiz_category10.*
import kotlinx.android.synthetic.main.activity_quiz_category2.*
import kotlinx.android.synthetic.main.activity_quiz_category7.*

class QuizCategory7 : AppCompatActivity() {
lateinit var timeCount:CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category7)

        startTime7.setOnClickListener {
            startTime7.visibility = View.INVISIBLE
            startClicked7()
        }
    }

    fun startClicked7() {

        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1+(milisUntilFinished / 1000)))

                if (radioButton71.isChecked||radioButton72.isChecked||radioButton73.isChecked||radioButton74.isChecked) {
                    optionupdate()
                }
                else {
                    next7.setOnClickListener {
                        next7.visibility=View.INVISIBLE
                        resetTimer()
                        optionupdate()
                        next7.visibility=View.VISIBLE
                    }

                }
            }
            override fun onFinish() {
                textView3.text = "Time Over!!"

                next10.setOnClickListener {
                    optionupdate()

                }
            }
        }.start()
    }

    fun resetTimer(){
        timeCount.start()
    }


    fun optionupdate() {
        if (radioButton71.isChecked || radioButton72.isChecked || radioButton73.isChecked || radioButton74.isChecked) {
            radioButton71.isEnabled = false
            radioButton72.isEnabled = false
            radioButton73.isEnabled = false
            radioButton74.isEnabled = false
            next7.setOnClickListener {
                resetTimer()
                resetoptions()
                radioButton71.isEnabled = true
                radioButton72.isEnabled = true
                radioButton73.isEnabled = true
                radioButton74.isEnabled = true
            }

        } else {
            next7.setOnClickListener {
                resetTimer()
            }
        }
    }

    fun resetoptions(){
        if(radioButton71.isChecked){
            radioButton71.isChecked=false
        }
        else if (radioButton72.isChecked){
            radioButton72.isChecked=false
        }
        else if (radioButton73.isChecked){
            radioButton73.isChecked=false
        }
        else {
            radioButton74.isChecked=false
        }
    }
}
