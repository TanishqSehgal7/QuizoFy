package com.example.quizofy

import android.content.Intent
import android.graphics.Color
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
import kotlinx.android.synthetic.main.activity_quiz_category3.*
import kotlinx.android.synthetic.main.activity_quiz_category3.textView5
import kotlinx.android.synthetic.main.activity_result.*

class QuizCategory3 : AppCompatActivity() {
    var correct=0
    var wrong=0
    var score=0
    var total=0
    val qlist= ArrayList<String>()
    var iterator = 0
    lateinit var ref:DatabaseReference
    lateinit var ref2: DatabaseReference
    private lateinit var databaseRef:DatabaseReference
    lateinit var timeCount :CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category3)
        Log.d("QuizCategory3","Quiz 3 oncreate called")
        qlist.add("Ques2")
        qlist.add("Ques3")
        qlist.add("Ques4")
        qlist.add("Ques5")

        radioButton31.isEnabled=false
        radioButton32.isEnabled=false
        radioButton33.isEnabled=false
        radioButton34.isEnabled=false

        databaseRef=FirebaseDatabase.getInstance().getReference("CATEGORIES")

        startTime3.setOnClickListener {
            getQuestion()
            startClicked3()
            Log.d("QuizCategory3","get question called and timer started")
            radioButton31.isEnabled=true
            radioButton32.isEnabled=true
            radioButton33.isEnabled=true
            radioButton34.isEnabled=true
            startTime3.visibility=View.INVISIBLE
            total++
        }
        submitButton3.setOnClickListener {
            resultKaIntent1()
            Toast.makeText(this,"Your Result",Toast.LENGTH_SHORT).show()
        }
    }

    fun startClicked3() {
        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1+(milisUntilFinished / 1000)))

                if (radioButton31.isChecked||radioButton32.isChecked||radioButton33.isChecked||radioButton34.isChecked) {
                    checkCorrectOrWrongForQ1()
                    radioButton31.isEnabled=false
                    radioButton32.isEnabled=false
                    radioButton33.isEnabled=false
                    radioButton34.isEnabled=false
                }
                else {
                    next3.setOnClickListener {
                        if (iterator <4) {
                            resetTimer()
                            updateQuestion()
                            radioButton31.isChecked=false
                            radioButton32.isChecked=false
                            radioButton33.isChecked=false
                            radioButton34.isChecked=false
                            radioButton31.isEnabled=true
                            radioButton32.isEnabled=true
                            radioButton33.isEnabled=true
                            radioButton34.isEnabled=true
                            radioButton31.setBackgroundColor(Color.TRANSPARENT)
                            radioButton32.setBackgroundColor(Color.TRANSPARENT)
                            radioButton33.setBackgroundColor(Color.TRANSPARENT)
                            radioButton34.setBackgroundColor(Color.TRANSPARENT)
                            if (radioButton31.isChecked||radioButton32.isChecked||radioButton33.isChecked||radioButton34.isChecked){
                                CorrectForRestOfTheQuestions()
                            }
                            next3.visibility = View.INVISIBLE
                            iterator++
                            next3.visibility = View.VISIBLE
                        }
                        else{
                            resultKaIntent1()
                        }
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

                    radioButton31.isEnabled=true
                    radioButton32.isEnabled=true
                    radioButton33.isEnabled=true
                    radioButton34.isEnabled=true
                    radioButton31.setBackgroundColor(Color.TRANSPARENT)
                    radioButton32.setBackgroundColor(Color.TRANSPARENT)
                    radioButton33.setBackgroundColor(Color.TRANSPARENT)
                    radioButton34.setBackgroundColor(Color.TRANSPARENT)
                    optionupdate()
                    resetoptions()
                    updateQuestion()
                    resetTimer()
                    if (radioButton31.isChecked||radioButton32.isChecked||radioButton33.isChecked||radioButton34.isChecked){
                        CorrectForRestOfTheQuestions()
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
        if (radioButton31.isChecked || radioButton32.isChecked || radioButton33.isChecked || radioButton34.isChecked) {

            resetoptions()

        } else {
            resetTimer()
            updateQuestion()
        }
    }

    fun resetoptions(){
        if(radioButton31.isChecked){
            radioButton31.isChecked=false
        }
        else if (radioButton32.isChecked){
            radioButton32.isChecked=false
        }
        else if (radioButton33.isChecked){
            radioButton33.isChecked=false
        }
        else {
            radioButton34.isChecked=false
        }
    }

    fun resultKaIntent1(){
        val intent =Intent(this,ResultActivity::class.java)
        startActivity(intent)
        correctAns.setText(correct)
        wrongAns.setText(wrong)
        ScoreTotal.setText(score)
    }


    fun getQuestion(){
        if (total > 5) {
            val intent = Intent(this, ResultActivity::class.java)
            Log.d("QuizCategory3","result activity started")
            startActivity(intent)
        } else
        {
            ref = databaseRef.child("General Knowledge").child("Ques1")
            Log.d("QuizCategory3","firebase data get" )
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory3","on data change for started"+dataSnapshot.children.toString())
                    val option1Text=dataSnapshot.child("option1").getValue().toString()
                    val option2Text=dataSnapshot.child("option2").getValue().toString()
                    val option3Text=dataSnapshot.child("option3").getValue().toString()
                    val option4Text=dataSnapshot.child("option4").getValue().toString()
                    val correctAns=dataSnapshot.child("correct").getValue().toString()
                    val questionText=dataSnapshot.child("question").getValue().toString()
                    textView5.setText(questionText)
                    radioButton31.setText(option1Text)
                    radioButton32.setText(option2Text)
                    radioButton33.setText(option3Text)
                    radioButton34.setText(option4Text)
                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("QuizCategory3","Something Went Wrong!!")
                }
            })
        }
    }


    fun updateQuestion(){
        ref2=databaseRef.child("General Knowledge").child(qlist.get(iterator))

        ref2.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val option1Text=dataSnapshot.child("option1").getValue().toString()
                val option2Text=dataSnapshot.child("option2").getValue().toString()
                val option3Text=dataSnapshot.child("option3").getValue().toString()
                val option4Text=dataSnapshot.child("option4").getValue().toString()
                val questionText=dataSnapshot.child("question").getValue().toString()
                val correct=dataSnapshot.child("correct").getValue().toString()
                textView5.setText(questionText)
                radioButton31.setText(option1Text)
                radioButton32.setText(option2Text)
                radioButton33.setText(option3Text)
                radioButton34.setText(option4Text)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory3","Something went Wrong!!")
            }

        })

    }


    fun checkCorrectOrWrongForQ1(){
        ref=databaseRef.child("General Knowledge").child("Ques1")
        ref.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory3","Something went Wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val correctAns=dataSnapshot.child("correct").getValue().toString()
                if (radioButton31.isChecked) {
                    if(radioButton31.text.toString().equals(correctAns)){
                        correct++
                        radioButton31.setBackgroundColor(Color.GREEN)
                        score++

                    }
                    else{
                        wrong++
                        radioButton31.setBackgroundColor(Color.RED)
                    }

                }


                if (radioButton32.isChecked) {
                    if(radioButton32.text.toString().equals(correctAns)){
                        correct++
                        radioButton32.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton32.setBackgroundColor(Color.RED)
                    }

                }


                if (radioButton33.isChecked) {
                    if(radioButton33.text.toString().equals(correctAns)){
                        correct++
                        radioButton33.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton33.setBackgroundColor(Color.RED)
                    }
                }

                if (radioButton34.isChecked) {
                    if(radioButton34.text.toString().equals(correctAns)){
                        correct++
                        radioButton34.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton34.setBackgroundColor(Color.RED)
                    }
                }
            }
        })
    }


    fun CorrectForRestOfTheQuestions(){

        ref2=databaseRef.child("General Knowledge").child(qlist.get(iterator))

        ref2.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory3","Something went wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val otherCorrectAns=dataSnapshot.child("correct").getValue().toString()
                if (radioButton31.isChecked) {
                    if(radioButton31.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton31.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton31.setBackgroundColor(Color.RED)
                    }

                }

                if (radioButton32.isChecked) {
                    if(radioButton32.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton32.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton32.setBackgroundColor(Color.RED)
                    }

                }

                if(radioButton33.isChecked) {
                    if(radioButton33.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton33.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton33.setBackgroundColor(Color.RED)
                    }
                }

                if (radioButton34.isChecked) {
                    if(radioButton34.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton34.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton34.setBackgroundColor(Color.RED)
                    }
                }
            }
        })
    }

}
