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
import kotlinx.android.synthetic.main.activity_quiz_category1.textView3
import kotlinx.android.synthetic.main.activity_quiz_category10.*
import kotlinx.android.synthetic.main.activity_quiz_category10.textView5
import kotlinx.android.synthetic.main.activity_quiz_category9.*
import kotlinx.android.synthetic.main.activity_result.*

class QuizCategory10 : AppCompatActivity() {
    var correct=0
    var wrong=0
    var score=0
    var total=0
    val qlist= ArrayList<String>()
    var iterator = 0
    lateinit var ref: DatabaseReference
    lateinit var ref2: DatabaseReference
    private lateinit var databaseRef: DatabaseReference
    lateinit var timeCount :CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category10)
        Log.d("QuizCategory10","Quiz 10 oncreate called")
        qlist.add("Ques2")
        qlist.add("Ques3")
        qlist.add("Ques4")
        qlist.add("Ques5")

        radioButton01.isEnabled=false
        radioButton02.isEnabled=false
        radioButton03.isEnabled=false
        radioButton04.isEnabled=false

        databaseRef= FirebaseDatabase.getInstance().getReference("CATEGORIES")

        startTime10.setOnClickListener {
            getQuestion()
            startClicked10()
            Log.d("QuizCategory10","get question called and timer started")
            radioButton01.isEnabled=true
            radioButton02.isEnabled=true
            radioButton03.isEnabled=true
            radioButton04.isEnabled=true
            startTime10.visibility=View.INVISIBLE
            total++
        }
        submitButton10.setOnClickListener {
            resultKaIntent1()
            Toast.makeText(this,"Your Result",Toast.LENGTH_SHORT).show()
        }
    }

    fun startClicked10() {
        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1+(milisUntilFinished / 1000)))

                if (radioButton01.isChecked||radioButton02.isChecked||radioButton03.isChecked||radioButton04.isChecked) {
                    checkCorrectOrWrongForQ1()
                    radioButton01.isEnabled=false
                    radioButton02.isEnabled=false
                    radioButton03.isEnabled=false
                    radioButton04.isEnabled=false
                }
                else {
                    next10.setOnClickListener {
                        if (iterator <4) {
                            resetTimer()
                            updateQuestion()
                            radioButton01.isChecked=false
                            radioButton02.isChecked=false
                            radioButton03.isChecked=false
                            radioButton04.isChecked=false
                            radioButton01.isEnabled=true
                            radioButton02.isEnabled=true
                            radioButton03.isEnabled=true
                            radioButton04.isEnabled=true
                            radioButton01.setBackgroundColor(Color.TRANSPARENT)
                            radioButton02.setBackgroundColor(Color.TRANSPARENT)
                            radioButton03.setBackgroundColor(Color.TRANSPARENT)
                            radioButton04.setBackgroundColor(Color.TRANSPARENT)
                            if (radioButton01.isChecked||radioButton02.isChecked||radioButton03.isChecked||radioButton04.isChecked){
                                CorrectForRestOfTheQuestions()
                            }
                            next10.visibility = View.INVISIBLE
                            iterator++
                            next10.visibility = View.VISIBLE
                        }
                        else{
                            resultKaIntent1()
                        }
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

                    radioButton01.isEnabled=true
                    radioButton02.isEnabled=true
                    radioButton03.isEnabled=true
                    radioButton04.isEnabled=true
                    radioButton01.setBackgroundColor(Color.TRANSPARENT)
                    radioButton02.setBackgroundColor(Color.TRANSPARENT)
                    radioButton03.setBackgroundColor(Color.TRANSPARENT)
                    radioButton04.setBackgroundColor(Color.TRANSPARENT)
                    optionupdate()
                    resetoptions()
                    updateQuestion()
                    resetTimer()
                    if (radioButton01.isChecked||radioButton02.isChecked||radioButton03.isChecked||radioButton04.isChecked){
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
        if (radioButton01.isChecked || radioButton02.isChecked || radioButton03.isChecked || radioButton04.isChecked) {

            resetoptions()

        } else {
            resetTimer()
            updateQuestion()
        }
    }

    fun resetoptions(){
        if(radioButton01.isChecked){
            radioButton01.isChecked=false
        }
        else if (radioButton02.isChecked){
            radioButton02.isChecked=false
        }
        else if (radioButton03.isChecked){
            radioButton03.isChecked=false
        }
        else {
            radioButton04.isChecked=false
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
            Log.d("QuizCategory10","result activity started")
            startActivity(intent)
        } else
        {
            ref = databaseRef.child("Politics").child("Ques1")
            Log.d("QuizCategory10","firebase data get" )
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory10","on data change for started"+dataSnapshot.children.toString())
                    val option1Text=dataSnapshot.child("option1").getValue().toString()
                    val option2Text=dataSnapshot.child("option2").getValue().toString()
                    val option3Text=dataSnapshot.child("option3").getValue().toString()
                    val option4Text=dataSnapshot.child("option4").getValue().toString()
                    val correctAns=dataSnapshot.child("correct").getValue().toString()
                    val questionText=dataSnapshot.child("question").getValue().toString()
                    textView5.setText(questionText)
                    radioButton01.setText(option1Text)
                    radioButton02.setText(option2Text)
                    radioButton03.setText(option3Text)
                    radioButton04.setText(option4Text)
                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("QuizCategory10","Something Went Wrong!!")
                }
            })
        }
    }


    fun updateQuestion(){
        ref2=databaseRef.child("Politics").child(qlist.get(iterator))

        ref2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val option1Text=dataSnapshot.child("option1").getValue().toString()
                val option2Text=dataSnapshot.child("option2").getValue().toString()
                val option3Text=dataSnapshot.child("option3").getValue().toString()
                val option4Text=dataSnapshot.child("option4").getValue().toString()
                val questionText=dataSnapshot.child("question").getValue().toString()
                val correct=dataSnapshot.child("correct").getValue().toString()
                textView5.setText(questionText)
                radioButton01.setText(option1Text)
                radioButton02.setText(option2Text)
                radioButton03.setText(option3Text)
                radioButton04.setText(option4Text)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory10","Something went Wrong!!")
            }

        })

    }


    fun checkCorrectOrWrongForQ1(){
        ref=databaseRef.child("Sneaker Culture").child("Ques1")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory10","Something went Wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val correctAns=dataSnapshot.child("correct").getValue().toString()
                if (radioButton01.isChecked) {
                    if(radioButton01.text.toString().equals(correctAns)){
                        correct++
                        radioButton01.setBackgroundColor(Color.GREEN)
                        score++

                    }
                    else{
                        wrong++
                        radioButton01.setBackgroundColor(Color.RED)
                    }

                }


                if (radioButton02.isChecked) {
                    if(radioButton02.text.toString().equals(correctAns)){
                        correct++
                        radioButton02.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton02.setBackgroundColor(Color.RED)
                    }

                }


                if (radioButton03.isChecked) {
                    if(radioButton03.text.toString().equals(correctAns)){
                        correct++
                        radioButton03.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton03.setBackgroundColor(Color.RED)
                    }
                }

                if (radioButton04.isChecked) {
                    if(radioButton04.text.toString().equals(correctAns)){
                        correct++
                        radioButton04.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton04.setBackgroundColor(Color.RED)
                    }
                }
            }
        })
    }


    fun CorrectForRestOfTheQuestions(){

        ref2=databaseRef.child("Politics").child(qlist.get(iterator))

        ref2.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory10","Something went wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val otherCorrectAns=dataSnapshot.child("correct").getValue().toString()
                if (radioButton01.isChecked) {
                    if(radioButton01.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton01.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton01.setBackgroundColor(Color.RED)
                    }

                }

                if (radioButton02.isChecked) {
                    if(radioButton02.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton02.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton02.setBackgroundColor(Color.RED)
                    }

                }

                if(radioButton03.isChecked) {
                    if(radioButton03.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton03.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton03.setBackgroundColor(Color.RED)
                    }
                }

                if (radioButton04.isChecked) {
                    if(radioButton04.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton04.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton04.setBackgroundColor(Color.RED)
                    }
                }
            }
        })
    }
}
