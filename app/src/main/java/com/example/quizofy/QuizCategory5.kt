package com.example.quizofy

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_quiz_category1.textView3
import kotlinx.android.synthetic.main.activity_quiz_category5.*

class QuizCategory5 : AppCompatActivity() {
    var correct=0
    var wrong=0
    var score=0
    var total=0
    lateinit var ref:DatabaseReference
    private lateinit var databaseRef:DatabaseReference
    lateinit var timeCount:CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category5)

        radioButton51.isEnabled=false
        radioButton52.isEnabled=false
        radioButton53.isEnabled=false
        radioButton54.isEnabled=false

        startTime5.setOnClickListener {
            startClicked5()
            radioButton51.isEnabled=true
            radioButton52.isEnabled=true
            radioButton53.isEnabled=true
            radioButton54.isEnabled=true
            startTime5.visibility=View.INVISIBLE

        }

        submitButton5.setOnClickListener {
            resultKaIntent5()
            Toast.makeText(this,"Your Result", Toast.LENGTH_SHORT).show()
        }

    }

    fun startClicked5() {

        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1+(milisUntilFinished / 1000)))

                if (radioButton51.isChecked||radioButton52.isChecked||radioButton53.isChecked||radioButton54.isChecked) {
                    optionupdate()
                }
                else {
                    next5.setOnClickListener {
                        next5.visibility=View.INVISIBLE
                        resetTimer()
                        optionupdate()
                        next5.visibility=View.VISIBLE
                    }

                }
            }
            override fun onFinish() {
                textView3.text = "Time Over!!"
                radioButton51.isEnabled=false
                radioButton52.isEnabled=false
                radioButton53.isEnabled=false
                radioButton54.isEnabled=false
                next5.setOnClickListener {
                    Toast.makeText(this@QuizCategory5,"Press Next to reset the timer",Toast.LENGTH_SHORT).show()
                    radioButton51.isEnabled=true
                    radioButton52.isEnabled=true
                    radioButton53.isEnabled=true
                    radioButton54.isEnabled=true
                    optionupdate()

                }
            }
        }.start()
    }

    fun resetTimer(){
        timeCount.start()
    }


    fun optionupdate() {
        if (radioButton51.isChecked || radioButton52.isChecked || radioButton53.isChecked || radioButton54.isChecked) {
            radioButton51.isEnabled = false
            radioButton52.isEnabled = false
            radioButton53.isEnabled = false
            radioButton54.isEnabled = false
            next5.setOnClickListener {
                resetTimer()
                resetoptions()
                radioButton51.isEnabled = true
                radioButton52.isEnabled = true
                radioButton53.isEnabled = true
                radioButton54.isEnabled = true
            }

        } else {
            next5.setOnClickListener {
                resetTimer()
            }
        }
    }

    fun resetoptions(){
        if(radioButton51.isChecked){
            radioButton51.isChecked=false
            radioButton51.isEnabled=false
            radioButton52.isEnabled=false
            radioButton53.isEnabled=false
            radioButton54.isEnabled=false
        }
        else if (radioButton52.isChecked){
            radioButton52.isChecked=false
            radioButton51.isChecked=false
            radioButton51.isEnabled=false
            radioButton52.isEnabled=false
            radioButton53.isEnabled=false
            radioButton54.isEnabled=false
        }
        else if (radioButton53.isChecked){
            radioButton53.isChecked=false
            radioButton51.isChecked=false
            radioButton51.isEnabled=false
            radioButton52.isEnabled=false
            radioButton53.isEnabled=false
            radioButton54.isEnabled=false
        }
        else {
            radioButton54.isChecked=false
            radioButton51.isChecked=false
            radioButton51.isEnabled=false
            radioButton52.isEnabled=false
            radioButton53.isEnabled=false
            radioButton54.isEnabled=false
        }
    }


    fun resultKaIntent5(){
        val intent = Intent(this,ResultActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun getQuestion(){

    }
}
