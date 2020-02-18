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
import kotlinx.android.synthetic.main.activity_quiz_category2.*
import kotlinx.android.synthetic.main.activity_quiz_category4.*

class QuizCategory4 : AppCompatActivity() {
lateinit var timeCount:CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category4)

        radioButton41.isEnabled=false
        radioButton42.isEnabled=false
        radioButton43.isEnabled=false
        radioButton44.isEnabled=false

        startTime4.setOnClickListener {
            radioButton41.isEnabled=true
            radioButton42.isEnabled=true
            radioButton43.isEnabled=true
            radioButton44.isEnabled=true
            startTime4.visibility=View.INVISIBLE
            startClicked4()
        }

        submitButton4.setOnClickListener {
            resultKaIntent4()
            Toast.makeText(this,"Your Result", Toast.LENGTH_SHORT).show()
        }

    }

    fun startClicked4() {

        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1+(milisUntilFinished / 1000)))

                if (radioButton41.isChecked||radioButton42.isChecked||radioButton43.isChecked||radioButton44.isChecked) {
                    optionupdate()
                }
                else {
                    next4.setOnClickListener {
                        next4.visibility=View.INVISIBLE
                        resetTimer()
                        optionupdate()
                        next4.visibility=View.VISIBLE
                    }

                }
            }
            override fun onFinish() {
                textView3.text = "Time Over!!"
                radioButton41.isEnabled=false
                radioButton42.isEnabled=false
                radioButton43.isEnabled=false
                radioButton44.isEnabled=false
                next4.setOnClickListener {
                    Toast.makeText(this@QuizCategory4,"Press Next again to reset timer",Toast.LENGTH_SHORT).show()
                    optionupdate()
                    radioButton41.isEnabled=true
                    radioButton42.isEnabled=true
                    radioButton43.isEnabled=true
                    radioButton44.isEnabled=true
                }
            }
        }.start()
    }

    fun resetTimer(){
        timeCount.start()
    }


    fun optionupdate() {
        if (radioButton41.isChecked || radioButton42.isChecked || radioButton43.isChecked || radioButton44.isChecked) {
            radioButton41.isEnabled = false
            radioButton42.isEnabled = false
            radioButton43.isEnabled = false
            radioButton44.isEnabled = false
            next4.setOnClickListener {
                resetTimer()
                resetoptions()
                radioButton41.isEnabled = true
                radioButton42.isEnabled = true
                radioButton43.isEnabled = true
                radioButton44.isEnabled = true
            }

        } else {
            next4.setOnClickListener {
                resetTimer()
            }
        }
    }

    fun resetoptions(){
        if(radioButton41.isChecked){
            radioButton41.isChecked=false
        }
        else if (radioButton42.isChecked){
            radioButton42.isChecked=false
        }
        else if (radioButton43.isChecked){
            radioButton43.isChecked=false
        }
        else {
            radioButton44.isChecked=false
        }
    }

    fun resultKaIntent4(){
        val intent = Intent(this,ResultActivity::class.java)
        startActivity(intent)
        finish()
    }
}
