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
import kotlinx.android.synthetic.main.activity_quiz_category8.*
import kotlinx.android.synthetic.main.activity_quiz_category9.*

class QuizCategory9 : AppCompatActivity() {
lateinit var timeCount:CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category9)

        radioButton91.isEnabled=false
        radioButton92.isEnabled=false
        radioButton93.isEnabled=false
        radioButton94.isEnabled=false


        startTime9.setOnClickListener {
            radioButton91.isEnabled=true
            radioButton92.isEnabled=true
            radioButton93.isEnabled=true
            radioButton94.isEnabled=true
            startTime9.visibility=View.INVISIBLE
            startClicked9()
        }

        submitButton9.setOnClickListener {
            resultKaIntent9()
            Toast.makeText(this,"Your Result", Toast.LENGTH_SHORT).show()
        }
    }

    fun startClicked9() {

        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1+(milisUntilFinished / 1000)))

                if (radioButton91.isChecked||radioButton92.isChecked||radioButton93.isChecked||radioButton94.isChecked) {
                    optionupdate()
                }
                else {
                    next9.setOnClickListener {
                        next9.visibility=View.INVISIBLE
                        resetTimer()
                        optionupdate()
                        next9.visibility=View.VISIBLE
                    }

                }
            }
            override fun onFinish() {
                textView3.text = "Time Over!!"

                next9.setOnClickListener {
                    Toast.makeText(this@QuizCategory9,"Press Next again to reset timer",Toast.LENGTH_SHORT).show()
                    optionupdate()
                    radioButton91.isEnabled=true
                    radioButton92.isEnabled=true
                    radioButton93.isEnabled=true
                    radioButton94.isEnabled=true

                }
            }
        }.start()
    }

    fun resetTimer(){
        timeCount.start()
    }


    fun optionupdate() {
        if (radioButton91.isChecked || radioButton92.isChecked || radioButton93.isChecked || radioButton94.isChecked) {
            radioButton91.isEnabled = false
            radioButton92.isEnabled = false
            radioButton93.isEnabled = false
            radioButton94.isEnabled = false
            next9.setOnClickListener {
                resetTimer()
                resetoptions()
                radioButton91.isEnabled = true
                radioButton92.isEnabled = true
                radioButton93.isEnabled = true
                radioButton94.isEnabled = true
            }

        } else {
            next9.setOnClickListener {
                resetTimer()
            }
        }
    }

    fun resetoptions(){
        if(radioButton91.isChecked){
            radioButton91.isChecked=false
        }
        else if (radioButton92.isChecked){
            radioButton92.isChecked=false
        }
        else if (radioButton93.isChecked){
            radioButton93.isChecked=false
        }
        else {
            radioButton94.isChecked=false
        }
    }

    fun resultKaIntent9(){
        val intent = Intent(this,ResultActivity::class.java)
        startActivity(intent)
        finish()
    }
}
