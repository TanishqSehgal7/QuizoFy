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
import kotlinx.android.synthetic.main.activity_quiz_category4.*
import kotlinx.android.synthetic.main.activity_quiz_category4.textView5

class QuizCategory4 : AppCompatActivity() {
    var correct=0
    var wrong=0
    var score=0
    var total=0
    lateinit var ref: DatabaseReference
    lateinit var timeCount:CountDownTimer
    private lateinit var databaseRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category4)

        radioButton41.isEnabled=false
        radioButton42.isEnabled=false
        radioButton43.isEnabled=false
        radioButton44.isEnabled=false

        startTime4.setOnClickListener {
            databaseRef=FirebaseDatabase.getInstance().reference
            startClicked4()
            getQuestion()
            Log.d("QuizCategory4","get question called and timer started")
            total++
            radioButton41.isEnabled=true
            radioButton42.isEnabled=true
            radioButton43.isEnabled=true
            radioButton44.isEnabled=true
            startTime4.visibility=View.INVISIBLE
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
                    radioButton41.isEnabled=true
                    radioButton42.isEnabled=true
                    radioButton43.isEnabled=true
                    radioButton44.isEnabled=true
                    optionupdate()
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
            radioButton41.isEnabled=false
            radioButton42.isEnabled=false
            radioButton43.isEnabled=false
            radioButton44.isEnabled=false
        }
        else if (radioButton42.isChecked){
            radioButton42.isChecked=false
            radioButton41.isEnabled=false
            radioButton42.isEnabled=false
            radioButton43.isEnabled=false
            radioButton44.isEnabled=false
        }
        else if (radioButton43.isChecked){
            radioButton43.isChecked=false
            radioButton41.isEnabled=false
            radioButton42.isEnabled=false
            radioButton43.isEnabled=false
            radioButton44.isEnabled=false
        }
        else {
            radioButton44.isChecked=false
            radioButton41.isEnabled=false
            radioButton42.isEnabled=false
            radioButton43.isEnabled=false
            radioButton44.isEnabled=false
        }
    }

    fun resultKaIntent4(){
        val intent = Intent(this,ResultActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun getQuestion(){
        if (total > 5) {
            val intent = Intent(this, ResultActivity::class.java)
            Log.d("QuizCategory4","result activiuty started")
            startActivity(intent)
        } else
        {
            ref = databaseRef.child("CATEGORIES").child("ART AND LITERATURE")
                .child("Ques1")
            Log.d("QuizCategory4","firebase data get")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory4","on data change for started"+dataSnapshot.children.toString())

                    val option1Text=dataSnapshot.child("option1").value.toString()
                    val option2Text=dataSnapshot.child("option2").value.toString()
                    val option3Text=dataSnapshot.child("option3").value.toString()
                    val option4Text=dataSnapshot.child("option4").value.toString()
                    val correctAns=dataSnapshot.child("Correct").value.toString()
                    val questionText=dataSnapshot.child("question").value.toString()

                    textView5.setText(questionText)
                    radioButton41.setText(option1Text)
                    radioButton42.setText(option2Text)
                    radioButton43.setText(option3Text)
                    radioButton44.setText(option4Text)

                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("QuizCategory4","Something Went Wrong!!")
                }
            })
        }
    }
}
