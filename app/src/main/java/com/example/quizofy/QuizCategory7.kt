package com.example.quizofy

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_quiz_category1.textView3
import kotlinx.android.synthetic.main.activity_quiz_category7.*

class QuizCategory7 : AppCompatActivity() {
lateinit var timeCount:CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category7)

        radioButton71.isEnabled=false
        radioButton72.isEnabled=false
        radioButton73.isEnabled=false
        radioButton74.isEnabled=false

        startTime7.setOnClickListener {
            radioButton71.isEnabled=true
            radioButton72.isEnabled=true
            radioButton73.isEnabled=true
            radioButton74.isEnabled=true
            startTime7.visibility = View.INVISIBLE
            startClicked7()
        }

        submitButton7.setOnClickListener {
            resultKaIntent7()
            Toast.makeText(this,"Your Result", Toast.LENGTH_SHORT).show()
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
                radioButton71.isEnabled=false
                radioButton72.isEnabled=false
                radioButton73.isEnabled=false
                radioButton74.isEnabled=false
                next7.setOnClickListener {
                    Toast.makeText(this@QuizCategory7,"Press Next again to reset timer",Toast.LENGTH_SHORT).show()
                    radioButton71.isEnabled=true
                    radioButton72.isEnabled=true
                    radioButton73.isEnabled=true
                    radioButton74.isEnabled=true
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
            radioButton71.isEnabled=false
            radioButton72.isEnabled=false
            radioButton73.isEnabled=false
            radioButton74.isEnabled=false
        }
        else if (radioButton72.isChecked){
            radioButton72.isChecked=false
            radioButton71.isEnabled=false
            radioButton72.isEnabled=false
            radioButton73.isEnabled=false
            radioButton74.isEnabled=false
        }
        else if (radioButton73.isChecked){
            radioButton73.isChecked=false
            radioButton71.isEnabled=false
            radioButton72.isEnabled=false
            radioButton73.isEnabled=false
            radioButton74.isEnabled=false
        }
        else {
            radioButton74.isChecked=false
            radioButton71.isEnabled=false
            radioButton72.isEnabled=false
            radioButton73.isEnabled=false
            radioButton74.isEnabled=false
        }
    }

    fun resultKaIntent7(){
        val intent = Intent(this,ResultActivity::class.java)
        startActivity(intent)
    }
}
