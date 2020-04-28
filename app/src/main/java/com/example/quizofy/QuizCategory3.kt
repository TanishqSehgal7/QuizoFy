package com.example.quizofy

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_quiz_category1.*
import kotlinx.android.synthetic.main.activity_quiz_category1.textView3
import kotlinx.android.synthetic.main.activity_quiz_category3.*
import kotlinx.android.synthetic.main.activity_quiz_category3.textView5

class QuizCategory3 : AppCompatActivity() {
    var correct=0
    var wrong=0
    var score=0
    var total=0
    lateinit var ref: DatabaseReference
    private lateinit var databaseRef: DatabaseReference
    lateinit var timeCount:CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category3)

        radioButton31.isEnabled=false
        radioButton32.isEnabled=false
        radioButton33.isEnabled=false
        radioButton34.isEnabled=false

        startTime3.setOnClickListener {
            databaseRef=FirebaseDatabase.getInstance().reference
            startClicked3()
            getQuestion()
            Log.d("QuizCategory3","get question called and timer started")
            radioButton31.isEnabled=true
            radioButton32.isEnabled=true
            radioButton33.isEnabled=true
            radioButton34.isEnabled=true
            startTime3.visibility=View.INVISIBLE
            total++
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

    fun getQuestion(){
        if (total > 5) {
            val intent = Intent(this, ResultActivity::class.java)
            Log.d("QuizCategory1","result activiuty started")
            startActivity(intent)
        } else
        {
            ref = databaseRef.child("CATEGORIES").child("General Knowledge")
                .child("Ques1")
            Log.d("QuizCategory3","firebase data get")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory3","on data change for started"+dataSnapshot.children.toString())

                    val option1Text=dataSnapshot.child("option1").value.toString()
                    val option2Text=dataSnapshot.child("option2").value.toString()
                    val option3Text=dataSnapshot.child("option3").value.toString()
                    val option4Text=dataSnapshot.child("option4").value.toString()
                    val correctAns=dataSnapshot.child("Correct").value.toString()
                    val questionText=dataSnapshot.child("question").value.toString()

                    textView5.setText(questionText)
                    radioButton31.setText(option1Text)
                    radioButton32.setText(option2Text)
                    radioButton33.setText(option3Text)
                    radioButton34.setText(option4Text)

                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("QuizCategory1","Something Went Wrong!!")
                }
            })
        }
    }
}
