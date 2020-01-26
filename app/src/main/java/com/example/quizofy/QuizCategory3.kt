package com.example.quizofy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quiz_category1.*
import kotlinx.android.synthetic.main.activity_quiz_category1.textView3
import kotlinx.android.synthetic.main.activity_quiz_category10.*
import kotlinx.android.synthetic.main.activity_quiz_category3.*

class QuizCategory3 : AppCompatActivity() {
lateinit var timeCount:CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category3)

        startTime3.setOnClickListener {
            startTime3.visibility=View.INVISIBLE
            startClicked3()
        }

        submitButton3.setOnClickListener {
            resultKaIntent3()
            Toast.makeText(this,"Your Result", Toast.LENGTH_SHORT).show()
        }

    }

    fun startClicked3() {

        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1+(milisUntilFinished / 1000)))

                if (radioButton31.isChecked||radioButton32.isChecked||radioButton33.isChecked||radioButton34.isChecked) {
                    optionupdate()
                }
                else {
                    next3.setOnClickListener {
                        next3.visibility=View.INVISIBLE
                        resetTimer()
                        optionupdate()
                        next3.visibility=View.VISIBLE
                    }

                }
            }
            override fun onFinish() {
                textView3.text = "Time Over!!"

                next3.setOnClickListener {
                    optionupdate()

                }
            }
        }.start()
    }

    fun resetTimer(){
        timeCount.start()
    }


    fun optionupdate() {
        if (radioButton31.isChecked || radioButton32.isChecked || radioButton33.isChecked || radioButton34.isChecked) {
            radioButton31.isEnabled = false
            radioButton32.isEnabled = false
            radioButton33.isEnabled = false
            radioButton34.isEnabled = false
            next3.setOnClickListener {
                resetTimer()
                resetoptions()
                radioButton31.isEnabled = true
                radioButton32.isEnabled = true
                radioButton33.isEnabled = true
                radioButton34.isEnabled = true
            }

        } else {
            next3.setOnClickListener {
                resetTimer()
            }
        }
    }

    fun resetoptions(){
        if(radioButton31.isChecked){
            radioButton31.isChecked=false
        }
        else if (radioButton32.isChecked){
            radioButton32.isChecked=false
        }
        else if (radioButton33.isChecked){
            radioButton33.isChecked=false
        }
        else {
            radioButton34.isChecked=false
        }
    }


    fun resultKaIntent3(){
        val intent = Intent(this,ResultActivity::class.java)
        startActivity(intent)
    }
}
