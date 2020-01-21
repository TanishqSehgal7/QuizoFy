package com.example.quizofy

import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import kotlinx.android.synthetic.main.activity_quiz_category1.*
import kotlinx.android.synthetic.main.activity_quiz_category1.textView3
import kotlinx.android.synthetic.main.activity_quiz_category10.*
import kotlinx.android.synthetic.main.activity_quiz_category2.*

class QuizCategory2 : AppCompatActivity() {
lateinit var timeCount:CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category2)

        startTime2.setOnClickListener {
            startTime2.visibility=View.INVISIBLE
            startClicked2()
        }
    }

    fun startClicked2() {

        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1+(milisUntilFinished / 1000)))

                if (radioButton21.isChecked||radioButton22.isChecked||radioButton23.isChecked||radioButton24.isChecked) {
                    optionupdate()
                }
                else {
                    next2.setOnClickListener {
                        next2.visibility=View.INVISIBLE
                        resetTimer()
                        optionupdate()
                        next2.visibility=View.VISIBLE
                    }

                }
            }
            override fun onFinish() {
                textView3.text = "Time Over!!"

                next2.setOnClickListener {
                    optionupdate()

                }
            }
        }.start()
    }

    fun resetTimer(){
        timeCount.start()
    }


    fun optionupdate() {
        if (radioButton21.isChecked || radioButton22.isChecked || radioButton23.isChecked || radioButton24.isChecked) {
            radioButton21.isEnabled = false
            radioButton22.isEnabled = false
            radioButton23.isEnabled = false
            radioButton24.isEnabled = false
            next2.setOnClickListener {
                resetTimer()
                resetoptions()
                radioButton21.isEnabled = true
                radioButton22.isEnabled = true
                radioButton23.isEnabled = true
                radioButton24.isEnabled = true
            }

        } else {
            next2.setOnClickListener {
                resetTimer()
            }
        }
    }

    fun resetoptions(){
        if(radioButton21.isChecked){
            radioButton21.isChecked=false
        }
        else if (radioButton22.isChecked){
            radioButton22.isChecked=false
        }
        else if (radioButton23.isChecked){
            radioButton23.isChecked=false
        }
        else {
            radioButton24.isChecked=false
        }
    }
}
