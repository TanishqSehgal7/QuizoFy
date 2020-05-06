package com.example.quizofy

import android.content.Intent
import android.content.IntentSender
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
    var iterator = 0
    var iterator2=0
    lateinit var ref:DatabaseReference
    lateinit var ref2: DatabaseReference
    private lateinit var databaseRef:DatabaseReference
    lateinit var timeCount :CountDownTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category1)
        Log.d("QuizCategory1","Quiz 1 oncreate called")
        qlist.add("Ques2")
        qlist.add("Ques3")
        qlist.add("Ques4")
        qlist.add("Ques5")

        radioButton11.isEnabled=false
        radioButton12.isEnabled=false
        radioButton13.isEnabled=false
        radioButton14.isEnabled=false

        databaseRef=FirebaseDatabase.getInstance().getReference("CATEGORIES")

        startTime.setOnClickListener {
                startClicked1()
                getQuestion()
                Log.d("QuizCategory1","get question called and timer started")
                radioButton11.isEnabled=true
                radioButton12.isEnabled=true
                radioButton13.isEnabled=true
                radioButton14.isEnabled=true
                startTime.visibility=View.INVISIBLE
                total++
        }

        submitButton1.setOnClickListener {
            resultKaIntent1()
            Toast.makeText(this,"Your Result",Toast.LENGTH_SHORT).show()
        }
    }


    fun startClicked1() {
        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1 + (milisUntilFinished / 1000)))
                    if (radioButton11.isChecked || radioButton12.isChecked || radioButton13.isChecked || radioButton14.isChecked) {
                        checkCorrectOrWrongForQ1()
                        radioButton11.isEnabled = false
                        radioButton12.isEnabled = false
                        radioButton13.isEnabled = false
                        radioButton14.isEnabled = false
                    } else {
                        next1.setOnClickListener {
                            if (iterator < 4) {
                                radioButton11.isChecked = false
                                radioButton12.isChecked = false
                                radioButton13.isChecked = false
                                radioButton14.isChecked = false
                                radioButton11.isEnabled = true
                                radioButton12.isEnabled = true
                                radioButton13.isEnabled = true
                                radioButton14.isEnabled = true
                                radioButton11.setBackgroundColor(Color.TRANSPARENT)
                                radioButton12.setBackgroundColor(Color.TRANSPARENT)
                                radioButton13.setBackgroundColor(Color.TRANSPARENT)
                                radioButton14.setBackgroundColor(Color.TRANSPARENT)
                                updateQuestion()
                                resetTimer()
                                total++
                                if (radioButton11.isChecked || radioButton12.isChecked || radioButton13.isChecked || radioButton14.isChecked) {
                                  CorrectForTheQuestions()
                                }
                                next1.visibility = View.INVISIBLE
                                iterator++
                                next1.visibility = View.VISIBLE
                            } else {
                                resultKaIntent1()
                            }
                        }
                    }
                }
                override fun onFinish() {
                    textView3.text = "Time Over!!"
                    radioButton11.isEnabled = false
                    radioButton12.isEnabled = false
                    radioButton13.isEnabled = false
                    radioButton14.isEnabled = false
                    next1.setOnClickListener {

                        radioButton11.isEnabled = true
                        radioButton12.isEnabled = true
                        radioButton13.isEnabled = true
                        radioButton14.isEnabled = true
                        radioButton11.setBackgroundColor(Color.TRANSPARENT)
                        radioButton12.setBackgroundColor(Color.TRANSPARENT)
                        radioButton13.setBackgroundColor(Color.TRANSPARENT)
                        radioButton14.setBackgroundColor(Color.TRANSPARENT)
                        optionupdate()
                        resetoptions()
                        updateQuestion()
                        resetTimer()
                        if (radioButton11.isChecked || radioButton12.isChecked || radioButton13.isChecked || radioButton14.isChecked) {
                            CorrectForTheQuestions()
                        }
                        iterator++
                    }
                }
            }.start()

    }

    fun resetTimer(){
        timeCount.start()
    }

    fun optionupdate() {
        if (radioButton11.isChecked || radioButton12.isChecked || radioButton13.isChecked || radioButton14.isChecked) {
               resetoptions()
        } else {
                resetTimer()
                updateQuestion()
        }
    }

    fun resetoptions(){
        if(radioButton11.isChecked){
            radioButton11.isChecked=false
        }
        else if (radioButton12.isChecked){
            radioButton12.isChecked=false
        }
        else if (radioButton13.isChecked){
            radioButton13.isChecked=false
        }
        else {
            radioButton14.isChecked=false
        }
    }

    fun resultKaIntent1(){
        val intent =Intent(this,ResultActivity::class.java)
        startActivity(intent)
        correctAns.setText(correct)
        wrongAns.setText(wrong)
        ScoreTotal.setText(score)
//        finish()
    }


    fun getQuestion(){
        if (total > 5) {
            resultKaIntent1()
        } else
        {
            ref = databaseRef.child("Art and Literature")
                .child("Ques1")
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
                    if (radioButton11.isChecked||radioButton12.isChecked||radioButton13.isChecked||radioButton14.isChecked){
                        radioButton11.isEnabled=false
                        radioButton12.isEnabled=false
                        radioButton13.isEnabled=false
                        radioButton14.isEnabled=false
                        checkCorrectOrWrongForQ1()
                    }

                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("QuizCategory1","Something Went Wrong!!")
                }
            })
        }
    }


    fun updateQuestion(){
        if (total>5){
            resultKaIntent1()
        }
        else {
            ref2 = databaseRef.child("Art and Literature").child(qlist.get(iterator))
            ref2.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val option1Text = dataSnapshot.child("option1").getValue().toString()
                    val option2Text = dataSnapshot.child("option2").getValue().toString()
                    val option3Text = dataSnapshot.child("option3").getValue().toString()
                    val option4Text = dataSnapshot.child("option4").getValue().toString()
                    val questionText = dataSnapshot.child("question").getValue().toString()
                    val correct = dataSnapshot.child("correct").getValue().toString()
                    textView5.setText(questionText)
                    radioButton11.setText(option1Text)
                    radioButton12.setText(option2Text)
                    radioButton13.setText(option3Text)
                    radioButton14.setText(option4Text)
                    if (radioButton11.isChecked||radioButton12.isChecked||radioButton13.isChecked||radioButton14.isChecked){
                        radioButton11.isEnabled=false
                        radioButton12.isEnabled=false
                        radioButton13.isEnabled=false
                        radioButton14.isEnabled=false
                        CorrectForTheQuestions()
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    Log.d("QuizCategory1", "Something went Wrong!!")
                }

            })
        }
    }


    fun checkCorrectOrWrongForQ1(){
            ref = databaseRef.child("Art and Literature").child("Ques1")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.d("QuizCategory1", "Something went Wrong!!")
                }
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val correctAns = dataSnapshot.child("correct").getValue().toString()
                    if (radioButton11.isChecked) {
                        if (radioButton11.text.toString().equals(correctAns)) {
                            correct++
                            score++
                            radioButton11.setBackgroundColor(Color.GREEN)
                        } else {
                            wrong++
                            radioButton11.setBackgroundColor(Color.RED)
                        }

                    }

                    if (radioButton12.isChecked) {
                        if (radioButton12.text.toString().equals(correctAns)) {
                            correct++
                            radioButton12.setBackgroundColor(Color.GREEN)
                            score++
                        } else {
                            wrong++
                            radioButton12.setBackgroundColor(Color.RED)
                        }

                    }

                    if (radioButton13.isChecked) {
                        if (radioButton13.text.toString().equals(correctAns)) {
                            correct++
                            radioButton13.setBackgroundColor(Color.GREEN)
                            score++
                        } else {
                            wrong++
                            radioButton13.setBackgroundColor(Color.RED)
                        }
                    }

                    if (radioButton14.isChecked) {
                        if (radioButton14.text.toString().equals(correctAns)) {
                            correct++
                            radioButton14.setBackgroundColor(Color.GREEN)
                            score++
                        } else {
                            wrong++
                            radioButton14.setBackgroundColor(Color.RED)
                        }
                    }
                }
            })
    }


    fun CorrectForTheQuestions(){

        ref2=databaseRef.child("Art and Literature").child(qlist.get(iterator))

        ref2.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                    Log.d("QuizCategory1","Something went wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val otherCorrectAns=dataSnapshot.child("correct").getValue().toString()
                if (radioButton11.isChecked) {
                    if(radioButton11.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton11.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton11.setBackgroundColor(Color.RED)
                    }

                }

                if (radioButton12.isChecked) {
                    if(radioButton12.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton12.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton12.setBackgroundColor(Color.RED)
                    }

                }

                if(radioButton13.isChecked) {
                    if(radioButton13.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton13.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton13.setBackgroundColor(Color.RED)
                    }
                }

                if (radioButton14.isChecked) {
                    if(radioButton14.text.toString().equals(otherCorrectAns)){
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
        })
    }
}


