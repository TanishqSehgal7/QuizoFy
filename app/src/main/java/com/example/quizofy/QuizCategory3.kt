package com.example.quizofy

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_quiz_category1.textView3
import kotlinx.android.synthetic.main.activity_quiz_category3.*

class QuizCategory3 : AppCompatActivity() {
lateinit var timeCount:CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category3)

        radioButton31.isEnabled=false
        radioButton32.isEnabled=false
        radioButton33.isEnabled=false
        radioButton34.isEnabled=false

        startTime3.setOnClickListener {
            radioButton31.isEnabled=true
            radioButton32.isEnabled=true
            radioButton33.isEnabled=true
            radioButton34.isEnabled=true
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
                radioButton31.isEnabled=false
                radioButton32.isEnabled=false
                radioButton33.isEnabled=false
                radioButton34.isEnabled=false
                next3.setOnClickListener {
                    Toast.makeText(this@QuizCategory3,"Press next again to reset timer",Toast.LENGTH_SHORT).show()
                    radioButton31.isEnabled=true
                    radioButton32.isEnabled=true
                    radioButton33.isEnabled=true
                    radioButton34.isEnabled=true
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
            radioButton31.isEnabled=false
            radioButton32.isEnabled=false
            radioButton33.isEnabled=false
            radioButton34.isEnabled=false
        }
        else if (radioButton32.isChecked){
            radioButton32.isChecked=false
            radioButton31.isChecked=false
            radioButton31.isEnabled=false
            radioButton32.isEnabled=false
            radioButton33.isEnabled=false
            radioButton34.isEnabled=false
        }
        else if (radioButton33.isChecked){
            radioButton33.isChecked=false
            radioButton31.isChecked=false
            radioButton31.isEnabled=false
            radioButton32.isEnabled=false
            radioButton33.isEnabled=false
            radioButton34.isEnabled=false
        }
        else {
            radioButton34.isChecked=false
            radioButton31.isChecked=false
            radioButton31.isEnabled=false
            radioButton32.isEnabled=false
            radioButton33.isEnabled=false
            radioButton34.isEnabled=false
        }
    }


    fun resultKaIntent3(){
        val intent = Intent(this,ResultActivity::class.java)
        startActivity(intent)
        finish()
    }
}
