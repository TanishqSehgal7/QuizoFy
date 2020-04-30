package com.example.quizofy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.renderscript.Sampler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_quiz_category1.*
import kotlinx.android.synthetic.main.activity_result.*
import java.util.*
import kotlin.collections.ArrayList

class QuizCategory1 : AppCompatActivity() {
    var correct=0
    var wrong=0
    var score=0
    var total=0
    val qlist= ArrayList<String>()
    var i = 0
    lateinit var ref:DatabaseReference
    private lateinit var databaseRef:DatabaseReference
    lateinit var timeCount :CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category1)
        Log.d("QuizCategory1","Quiz 1 oncreate called")
        qlist.add("Ques1")
        qlist.add("Ques2")
        qlist.add("Ques3")
        qlist.add("Ques4")
        qlist.add("Ques5")

        radioButton11.isEnabled=false
        radioButton12.isEnabled=false
        radioButton13.isEnabled=false
        radioButton14.isEnabled=false

        startTime.setOnClickListener {
                databaseRef=FirebaseDatabase.getInstance().getReference("CATEGORIES")
                getQuestion()
                startClicked1()
                Log.d("QuizCategory1","get question called and timer started")
                radioButton11.isEnabled=true
                radioButton12.isEnabled=true
                radioButton13.isEnabled=true
                radioButton14.isEnabled=true
                startTime.visibility=View.INVISIBLE
                total++
//                checkCorrectorWrong()
        }


        submitButton1.setOnClickListener {
            resultKaIntent1()
            Toast.makeText(this,"Your Result",Toast.LENGTH_SHORT).show()
        }
    }

    fun startClicked1() {
        i=0

        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1+(milisUntilFinished / 1000)))

                if (radioButton11.isChecked||radioButton12.isChecked||radioButton13.isChecked||radioButton14.isChecked) {
                    optionupdate()
                    checkCorrectOrWrong()
                }
                else {
                    next1.setOnClickListener {
//                      UpdateQuestion()
                        next1.visibility=View.INVISIBLE
                        resetTimer()
                        optionupdate()
                        checkCorrectOrWrong()
                        if(i<5)
                            i++
                        next1.visibility=View.VISIBLE
                    }
                }
            }
            override fun onFinish() {
                textView3.text = "Time Over!!"
                radioButton11.isEnabled=false
                radioButton12.isEnabled=false
                radioButton13.isEnabled=false
                radioButton14.isEnabled=false
                next1.setOnClickListener {
                    Toast.makeText(this@QuizCategory1,"Press Next again to reset the timer",Toast.LENGTH_SHORT)
                        .show()
                    radioButton11.isEnabled=true
                    radioButton12.isEnabled=true
                    radioButton13.isEnabled=true
                    radioButton14.isEnabled=true
                    optionupdate()
                    i++
                    checkCorrectOrWrong()
                }
            }
        }.start()
    }

    fun resetTimer(){
        timeCount.start()
    }

    fun optionupdate() {
        if (radioButton11.isChecked || radioButton12.isChecked || radioButton13.isChecked || radioButton14.isChecked) {
            radioButton11.isEnabled = false
            radioButton12.isEnabled = false
            radioButton13.isEnabled = false
            radioButton14.isEnabled = false
           next1.setOnClickListener {
               resetTimer()
               resetoptions()
               radioButton11.isEnabled = true
               radioButton12.isEnabled = true
               radioButton13.isEnabled = true
               radioButton14.isEnabled = true
           }

        } else {
            next1.setOnClickListener {
                resetTimer()
            }
        }
    }

    fun resetoptions(){
        if(radioButton11.isChecked){
            radioButton11.isChecked=false
            radioButton11.isEnabled=false
            radioButton12.isEnabled=false
            radioButton13.isEnabled=false
            radioButton14.isEnabled=false
        }
        else if (radioButton12.isChecked){
            radioButton12.isChecked=false
            radioButton11.isEnabled=false
            radioButton12.isEnabled=false
            radioButton13.isEnabled=false
            radioButton14.isEnabled=false

        }
        else if (radioButton13.isChecked){
            radioButton13.isChecked=false
            radioButton11.isEnabled=false
            radioButton12.isEnabled=false
            radioButton13.isEnabled=false
            radioButton14.isEnabled=false
        }
        else {
            radioButton14.isChecked=false
            radioButton11.isEnabled=false
            radioButton12.isEnabled=false
            radioButton13.isEnabled=false
            radioButton14.isEnabled=false
        }
    }

    fun resultKaIntent1(){
        val intent =Intent(this,ResultActivity::class.java)
        startActivity(intent)
        correctAns.setText(correct.toString())
        wrongAns.setText(correct.toString())
        finish()
    }


    fun getQuestion(){
        if (total > 5) {
            val intent = Intent(this, ResultActivity::class.java)
            Log.d("QuizCategory1","result activiuty started")
            startActivity(intent)
        } else
        {
            ref = databaseRef.child("Art and Literature")
                .child(qlist.get(i))
            Log.d("QuizCategory1","firebase data get" )
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory1","on data change for started"+dataSnapshot.children.toString())
                    val option1Text=dataSnapshot.child("option1").getValue().toString()
                    val option2Text=dataSnapshot.child("option2").getValue().toString()
                    val option3Text=dataSnapshot.child("option3").getValue().toString()
                    val option4Text=dataSnapshot.child("option4").getValue().toString()
                    val correctAns=dataSnapshot.child("Correct").getValue().toString()
                    val questionText=dataSnapshot.child("question").getValue().toString()
                    textView5.setText(questionText)
                    radioButton11.setText(option1Text)
                    radioButton12.setText(option2Text)
                    radioButton13.setText(option3Text)
                    radioButton14.setText(option4Text)
                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("QuizCategory1","Something Went Wrong!!")
                }
            })
        }
    }


    fun checkCorrectOrWrong(){

        radioButton11.setOnClickListener {
            if(radioButton11.text.toString().equals(correct)){
                correct++
                radioButton11.setBackgroundColor(Color.GREEN)
                score++
            }
            else{
                wrong++
                radioButton11.setBackgroundColor(Color.RED)
            }
        }

        radioButton12.setOnClickListener {
            if(radioButton12.text.toString().equals(correct)){
                correct++
                radioButton12.setBackgroundColor(Color.GREEN)
                score++
            }
            else{
                wrong++
                radioButton12.setBackgroundColor(Color.RED)
            }
        }

        radioButton13.setOnClickListener {
            if(radioButton13.text.toString().equals(correct)){
                correct++
                radioButton13.setBackgroundColor(Color.GREEN)
                score++
            }
            else{
                wrong++
                radioButton13.setBackgroundColor(Color.RED)
            }
        }

        radioButton14.setOnClickListener {
            if(radioButton14.text.toString().equals(correct)){
                correct++
                radioButton14.setBackgroundColor(Color.GREEN)
                score++
            }
            else{
                wrong++
                radioButton14.setBackgroundColor(Color.RED)
            }
        }
    }
}


