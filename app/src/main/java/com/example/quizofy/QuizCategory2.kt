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
import kotlinx.android.synthetic.main.activity_quiz_category2.textView5
import kotlinx.android.synthetic.main.activity_result.*

class QuizCategory2 : AppCompatActivity() {
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
        setContentView(R.layout.activity_quiz_category2)
        Log.d("QuizCategory2","Quiz 2 oncreate called")
        qlist.add("Ques2")
        qlist.add("Ques3")
        qlist.add("Ques4")
        qlist.add("Ques5")

        radioButton21.isEnabled=false
        radioButton22.isEnabled=false
        radioButton23.isEnabled=false
        radioButton24.isEnabled=false

        databaseRef=FirebaseDatabase.getInstance().getReference("CATEGORIES")

        startTime2.setOnClickListener {
            getQuestion()
            startClicked2()
            Log.d("QuizCategory2","get question called and timer started")
            radioButton21.isEnabled=true
            radioButton22.isEnabled=true
            radioButton23.isEnabled=true
            radioButton24.isEnabled=true
            startTime2.visibility=View.INVISIBLE
            total++
        }
        submitButton2.setOnClickListener {
            resultKaIntent1()
            Toast.makeText(this,"Your Result",Toast.LENGTH_SHORT).show()
        }
    }

    fun startClicked2() {
        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1+(milisUntilFinished / 1000)))

                if (radioButton21.isChecked||radioButton22.isChecked||radioButton23.isChecked||radioButton24.isChecked) {
                    checkCorrectOrWrongForQ1()
                    radioButton21.isEnabled=false
                    radioButton22.isEnabled=false
                    radioButton23.isEnabled=false
                    radioButton24.isEnabled=false
                }
                else {
                    next2.setOnClickListener {
                        if (iterator <4) {
                            resetTimer()
                            updateQuestion()
                            radioButton21.isChecked=false
                            radioButton22.isChecked=false
                            radioButton23.isChecked=false
                            radioButton24.isChecked=false
                            radioButton21.isEnabled=true
                            radioButton22.isEnabled=true
                            radioButton23.isEnabled=true
                            radioButton24.isEnabled=true
                            radioButton21.setBackgroundColor(Color.TRANSPARENT)
                            radioButton22.setBackgroundColor(Color.TRANSPARENT)
                            radioButton23.setBackgroundColor(Color.TRANSPARENT)
                            radioButton24.setBackgroundColor(Color.TRANSPARENT)
                            if (radioButton21.isChecked||radioButton22.isChecked||radioButton23.isChecked||radioButton24.isChecked){
                                CorrectForRestOfTheQuestions()
                            }
                            next2.visibility = View.INVISIBLE
                            iterator++
                            next2.visibility = View.VISIBLE
                        }
                        else{
                            resultKaIntent1()
                        }
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

                    radioButton21.isEnabled=true
                    radioButton22.isEnabled=true
                    radioButton23.isEnabled=true
                    radioButton24.isEnabled=true
                    radioButton21.setBackgroundColor(Color.TRANSPARENT)
                    radioButton22.setBackgroundColor(Color.TRANSPARENT)
                    radioButton23.setBackgroundColor(Color.TRANSPARENT)
                    radioButton24.setBackgroundColor(Color.TRANSPARENT)
                    optionupdate()
                    resetoptions()
                    updateQuestion()
                    resetTimer()
                    if (radioButton21.isChecked||radioButton22.isChecked||radioButton23.isChecked||radioButton24.isChecked){
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
        if (radioButton21.isChecked || radioButton22.isChecked || radioButton23.isChecked || radioButton24.isChecked) {

            resetoptions()

        } else {
            resetTimer()
            updateQuestion()
        }
    }

    fun resetoptions(){
        if(radioButton21.isChecked){
            radioButton21.isChecked=false
        }
        else if (radioButton22.isChecked){
            radioButton22.isChecked=false
        }
        else if (radioButton23.isChecked){
            radioButton23.isChecked=false
        }
        else {
            radioButton24.isChecked=false
        }
    }

    fun resultKaIntent1(){
        val intent =Intent(this,ResultActivity::class.java)
        startActivity(intent)
        correctAns.setText(correct)
        wrongAns.setText(wrong)
        ScoreTotal.setText(score)
        finish()
    }


    fun getQuestion(){
        if (total > 5) {
            val intent = Intent(this, ResultActivity::class.java)
            Log.d("QuizCategory2","result activity started")
            startActivity(intent)
        } else
        {
            ref = databaseRef.child("Geography").child("Ques1")
            Log.d("QuizCategory2","firebase data get" )
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory2","on data change for started"+dataSnapshot.children.toString())
                    val option1Text=dataSnapshot.child("option1").getValue().toString()
                    val option2Text=dataSnapshot.child("option2").getValue().toString()
                    val option3Text=dataSnapshot.child("option3").getValue().toString()
                    val option4Text=dataSnapshot.child("option4").getValue().toString()
                    val correctAns=dataSnapshot.child("correct").getValue().toString()
                    val questionText=dataSnapshot.child("question").getValue().toString()
                    textView5.setText(questionText)
                    radioButton21.setText(option1Text)
                    radioButton22.setText(option2Text)
                    radioButton23.setText(option3Text)
                    radioButton24.setText(option4Text)
                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("QuizCategory2","Something Went Wrong!!")
                }
            })
        }
    }


    fun updateQuestion(){
        ref2=databaseRef.child("Geography").child(qlist.get(iterator))

        ref2.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val option1Text=dataSnapshot.child("option1").getValue().toString()
                val option2Text=dataSnapshot.child("option2").getValue().toString()
                val option3Text=dataSnapshot.child("option3").getValue().toString()
                val option4Text=dataSnapshot.child("option4").getValue().toString()
                val questionText=dataSnapshot.child("question").getValue().toString()
                val correct=dataSnapshot.child("correct").getValue().toString()
                textView5.setText(questionText)
                radioButton21.setText(option1Text)
                radioButton22.setText(option2Text)
                radioButton23.setText(option3Text)
                radioButton24.setText(option4Text)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory2","Something went Wrong!!")
            }

        })

    }


    fun checkCorrectOrWrongForQ1(){
        ref=databaseRef.child("Geography").child("Ques1")
        ref.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory2","Something went Wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val correctAns=dataSnapshot.child("correct").getValue().toString()
                if (radioButton21.isChecked) {
                    if(radioButton21.text.toString().equals(correctAns)){
                        correct++
                        radioButton21.setBackgroundColor(Color.GREEN)
                        score++

                    }
                    else{
                        wrong++
                        radioButton21.setBackgroundColor(Color.RED)
                    }

                }


                if (radioButton22.isChecked) {
                    if(radioButton22.text.toString().equals(correctAns)){
                        correct++
                        radioButton22.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton22.setBackgroundColor(Color.RED)
                    }

                }


                if (radioButton23.isChecked) {
                    if(radioButton23.text.toString().equals(correctAns)){
                        correct++
                        radioButton23.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton23.setBackgroundColor(Color.RED)
                    }
                }

                if (radioButton24.isChecked) {
                    if(radioButton24.text.toString().equals(correctAns)){
                        correct++
                        radioButton24.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton24.setBackgroundColor(Color.RED)
                    }
                }
            }
        })
    }


    fun CorrectForRestOfTheQuestions(){

        ref2=databaseRef.child("Geography").child(qlist.get(iterator))

        ref2.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory2","Something went wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val otherCorrectAns=dataSnapshot.child("correct").getValue().toString()
                if (radioButton21.isChecked) {
                    if(radioButton21.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton21.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton21.setBackgroundColor(Color.RED)
                    }

                }

                if (radioButton12.isChecked) {
                    if(radioButton22.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton22.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton22.setBackgroundColor(Color.RED)
                    }

                }

                if(radioButton13.isChecked) {
                    if(radioButton23.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton23.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton23.setBackgroundColor(Color.RED)
                    }
                }

                if (radioButton24.isChecked) {
                    if(radioButton24.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton24.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton24.setBackgroundColor(Color.RED)
                    }
                }
            }
        })
    }
}
