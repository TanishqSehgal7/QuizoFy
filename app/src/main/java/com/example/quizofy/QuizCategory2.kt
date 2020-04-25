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
import kotlinx.android.synthetic.main.activity_quiz_category2.*
import kotlinx.android.synthetic.main.activity_quiz_category2.textView5

class QuizCategory2 : AppCompatActivity() {
    var correct=0
    var wrong=0
    var score=0
    var total=0
    lateinit var timeCount:CountDownTimer
    lateinit var ref: DatabaseReference
    private lateinit var databaseRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category2)

        radioButton21.isEnabled=false
        radioButton22.isEnabled=false
        radioButton23.isEnabled=false
        radioButton24.isEnabled=false

        startTime2.setOnClickListener {
            databaseRef=FirebaseDatabase.getInstance().reference
            startClicked2()
            getQuestion()
            total++
            radioButton21.isEnabled=true
            radioButton22.isEnabled=true
            radioButton23.isEnabled=true
            radioButton24.isEnabled=true
            startTime2.visibility=View.INVISIBLE

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
                    radioButton21.isEnabled=true
                    radioButton22.isEnabled=true
                    radioButton23.isEnabled=true
                    radioButton24.isEnabled=true
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
            radioButton21.isEnabled=false
            radioButton22.isEnabled=false
            radioButton23.isEnabled=false
            radioButton24.isEnabled=false
        }
        else if (radioButton22.isChecked){
            radioButton22.isChecked=false
            radioButton21.isChecked=false
            radioButton21.isEnabled=false
            radioButton22.isEnabled=false
            radioButton23.isEnabled=false
            radioButton24.isEnabled=false
        }
        else if (radioButton23.isChecked){
            radioButton23.isChecked=false
            radioButton21.isChecked=false
            radioButton21.isEnabled=false
            radioButton22.isEnabled=false
            radioButton23.isEnabled=false
            radioButton24.isEnabled=false
        }
        else {
            radioButton24.isChecked=false
            radioButton21.isChecked=false
            radioButton21.isEnabled=false
            radioButton22.isEnabled=false
            radioButton23.isEnabled=false
            radioButton24.isEnabled=false

        }
    }


    fun resultKaIntent2(){
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
            ref = databaseRef.child("CATEGORIES").child("Entertainment")
                .child("Ques1")
            Log.d("QuizCategory2","firebase data get")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory2","on data change for started"+dataSnapshot.children.toString())

                    val option1Text=dataSnapshot.child("option1").value.toString()
                    val option2Text=dataSnapshot.child("option2").value.toString()
                    val option3Text=dataSnapshot.child("option3").value.toString()
                    val option4Text=dataSnapshot.child("option4").value.toString()
                    val correctAns=dataSnapshot.child("Correct").value.toString()
                    val questionText=dataSnapshot.child("question").value.toString()

                    textView5.setText(questionText)
                    radioButton21.setText(option1Text)
                    radioButton22.setText(option2Text)
                    radioButton23.setText(option3Text)
                    radioButton24.setText(option4Text)

                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("QuizCategory1","Something Went Wrong!!")
                }
            })
        }

    }
}
