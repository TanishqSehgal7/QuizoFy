package com.example.quizofy

import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quiz_category1.*
import kotlinx.android.synthetic.main.activity_quiz_category1.textView3
import kotlinx.android.synthetic.main.activity_quiz_category10.*
import kotlinx.android.synthetic.main.activity_quiz_category2.*

class QuizCategory2 : AppCompatActivity() {
lateinit var timeCount:CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category2)

        radioButton21.isEnabled=false
        radioButton22.isEnabled=false
        radioButton23.isEnabled=false
        radioButton24.isEnabled=false

        startTime2.setOnClickListener {
            radioButton21.isEnabled=true
            radioButton22.isEnabled=true
            radioButton23.isEnabled=true
            radioButton24.isEnabled=true
            startTime2.visibility=View.INVISIBLE
            startClicked2()
        }

        submitButton2.setOnClickListener {
            resultKaIntent2()
            Toast.makeText(this,"Your Result",Toast.LENGTH_SHORT).show()
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
                radioButton21.isEnabled=false
                radioButton22.isEnabled=false
                radioButton23.isEnabled=false
                radioButton24.isEnabled=false

                next2.setOnClickListener {
                    Toast.makeText(this@QuizCategory2,"Press Next again to reset the timer",Toast.LENGTH_SHORT).show()
                    optionupdate()
                    radioButton21.isEnabled=true
                    radioButton22.isEnabled=true
                    radioButton23.isEnabled=true
                    radioButton24.isEnabled=true
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


    fun resultKaIntent2(){
        val intent = Intent(this,ResultActivity::class.java)
        startActivity(intent)
    }
}
