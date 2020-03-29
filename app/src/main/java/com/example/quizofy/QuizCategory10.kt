package com.example.quizofy

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_quiz_category1.textView3
import kotlinx.android.synthetic.main.activity_quiz_category10.*

class QuizCategory10 : AppCompatActivity() {
lateinit var timeCount:CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category10)

        radioButton01.isEnabled=false
        radioButton02.isEnabled=false
        radioButton03.isEnabled=false
        radioButton04.isEnabled=false

        startTime10.setOnClickListener {
            radioButton01.isEnabled=true
            radioButton02.isEnabled=true
            radioButton03.isEnabled=true
            radioButton04.isEnabled=true
            startTime10.visibility=View.INVISIBLE
            startClicked10()
        }

        submitButton10.setOnClickListener {
            resultKaIntent10()
            Toast.makeText(this,"Your Result", Toast.LENGTH_SHORT).show()
        }
    }


    fun startClicked10() {


        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1+(milisUntilFinished / 1000)))

                if (radioButton01.isChecked||radioButton02.isChecked||radioButton03.isChecked||radioButton04.isChecked) {
                    optionupdate()
                }
                else {
                    next10.setOnClickListener {
                        next10.visibility=View.INVISIBLE
                        resetTimer()
                        optionupdate()
                        next10.visibility=View.VISIBLE
                    }

                }
            }
            override fun onFinish() {
                textView3.text = "Time Over!!"
                radioButton01.isEnabled=false
                radioButton02.isEnabled=false
                radioButton03.isEnabled=false
                radioButton04.isEnabled=false
                next10.setOnClickListener {
                    Toast.makeText(this@QuizCategory10,"Press Next again to reset timer",Toast.LENGTH_SHORT).show()
                    radioButton01.isEnabled=true
                    radioButton02.isEnabled=true
                    radioButton03.isEnabled=true
                    radioButton04.isEnabled=true
                    optionupdate()

                }
            }
        }.start()
    }


    fun resetTimer(){
        timeCount.start()
    }


    fun optionupdate() {
        if (radioButton01.isChecked || radioButton02.isChecked || radioButton03.isChecked || radioButton04.isChecked) {
            radioButton01.isEnabled = false
            radioButton02.isEnabled = false
            radioButton03.isEnabled = false
            radioButton04.isEnabled = false
            next10.setOnClickListener {
                resetTimer()
                resetoptions()
                radioButton01.isEnabled = true
                radioButton02.isEnabled = true
                radioButton03.isEnabled = true
                radioButton04.isEnabled = true
            }

        } else {
            next10.setOnClickListener {
                resetTimer()
            }
        }
    }


    fun resetoptions(){
        if(radioButton01.isChecked){
            radioButton01.isChecked=false
            radioButton01.isEnabled=false
            radioButton02.isEnabled=false
            radioButton03.isEnabled=false
            radioButton04.isEnabled=false
        }
        else if (radioButton02.isChecked){
            radioButton02.isChecked=false
            radioButton01.isEnabled=false
            radioButton02.isEnabled=false
            radioButton03.isEnabled=false
            radioButton04.isEnabled=false
        }
        else if (radioButton03.isChecked){
            radioButton03.isChecked=false
            radioButton01.isEnabled=false
            radioButton02.isEnabled=false
            radioButton03.isEnabled=false
            radioButton04.isEnabled=false
        }
        else {
            radioButton04.isChecked=false
            radioButton01.isEnabled=false
            radioButton02.isEnabled=false
            radioButton03.isEnabled=false
            radioButton04.isEnabled=false
        }
    }


    fun resultKaIntent10(){
        val intent = Intent(this,ResultActivity::class.java)
        startActivity(intent)
        finish()
    }
}
