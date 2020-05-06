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
import kotlinx.android.synthetic.main.activity_quiz_category3.*
import kotlinx.android.synthetic.main.activity_quiz_category4.*
import kotlinx.android.synthetic.main.activity_quiz_category5.*
import kotlinx.android.synthetic.main.activity_quiz_category5.textView5
import kotlinx.android.synthetic.main.activity_result.*

class QuizCategory5 : AppCompatActivity() {
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
        setContentView(R.layout.activity_quiz_category5)
        Log.d("QuizCategory5","Quiz 5 oncreate called")
        qlist.add("Ques2")
        qlist.add("Ques3")
        qlist.add("Ques4")
        qlist.add("Ques5")

        radioButton51.isEnabled=false
        radioButton52.isEnabled=false
        radioButton53.isEnabled=false
        radioButton54.isEnabled=false

        databaseRef= FirebaseDatabase.getInstance().getReference("CATEGORIES")

        startTime5.setOnClickListener {
            getQuestion()
            startClicked5()
            Log.d("QuizCategory5","get question called and timer started")
            radioButton51.isEnabled=true
            radioButton52.isEnabled=true
            radioButton53.isEnabled=true
            radioButton54.isEnabled=true
            startTime5.visibility=View.INVISIBLE
            total++
        }
        submitButton5.setOnClickListener {
            resultKaIntent1()
            Toast.makeText(this,"Your Result",Toast.LENGTH_SHORT).show()
        }
    }

    fun startClicked5() {
        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1+(milisUntilFinished / 1000)))

                if (radioButton51.isChecked||radioButton52.isChecked||radioButton53.isChecked||radioButton54.isChecked) {
                    checkCorrectOrWrongForQ1()
                    radioButton51.isEnabled=false
                    radioButton52.isEnabled=false
                    radioButton53.isEnabled=false
                    radioButton54.isEnabled=false
                }
                else {
                    next5.setOnClickListener {
                        if (iterator <4) {
                            resetTimer()
                            updateQuestion()
                            radioButton51.isChecked=false
                            radioButton52.isChecked=false
                            radioButton53.isChecked=false
                            radioButton54.isChecked=false
                            radioButton51.isEnabled=true
                            radioButton52.isEnabled=true
                            radioButton53.isEnabled=true
                            radioButton54.isEnabled=true
                            radioButton51.setBackgroundColor(Color.TRANSPARENT)
                            radioButton52.setBackgroundColor(Color.TRANSPARENT)
                            radioButton53.setBackgroundColor(Color.TRANSPARENT)
                            radioButton54.setBackgroundColor(Color.TRANSPARENT)
                            if (radioButton51.isChecked||radioButton52.isChecked||radioButton53.isChecked||radioButton54.isChecked){
                                CorrectForRestOfTheQuestions()
                            }
                            next5.visibility = View.INVISIBLE
                            iterator++
                            next5.visibility = View.VISIBLE
                        }
                        else{
                            resultKaIntent1()
                        }
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

                    radioButton51.isEnabled=true
                    radioButton52.isEnabled=true
                    radioButton53.isEnabled=true
                    radioButton54.isEnabled=true
                    radioButton51.setBackgroundColor(Color.TRANSPARENT)
                    radioButton52.setBackgroundColor(Color.TRANSPARENT)
                    radioButton53.setBackgroundColor(Color.TRANSPARENT)
                    radioButton54.setBackgroundColor(Color.TRANSPARENT)
                    optionupdate()
                    resetoptions()
                    updateQuestion()
                    resetTimer()
                    if (radioButton51.isChecked||radioButton52.isChecked||radioButton53.isChecked||radioButton54.isChecked){
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
        if (radioButton51.isChecked || radioButton52.isChecked || radioButton53.isChecked || radioButton54.isChecked) {

            resetoptions()

        } else {
            resetTimer()
            updateQuestion()
        }
    }

    fun resetoptions(){
        if(radioButton51.isChecked){
            radioButton51.isChecked=false
        }
        else if (radioButton52.isChecked){
            radioButton52.isChecked=false
        }
        else if (radioButton53.isChecked){
            radioButton53.isChecked=false
        }
        else {
            radioButton54.isChecked=false
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
            Log.d("QuizCategory5","result activity started")
            startActivity(intent)
        } else
        {
            ref = databaseRef.child("Technology").child("Ques1")
            Log.d("QuizCategory5","firebase data get" )
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory5","on data change for started"+dataSnapshot.children.toString())
                    val option1Text=dataSnapshot.child("option1").getValue().toString()
                    val option2Text=dataSnapshot.child("option2").getValue().toString()
                    val option3Text=dataSnapshot.child("option3").getValue().toString()
                    val option4Text=dataSnapshot.child("option4").getValue().toString()
                    val correctAns=dataSnapshot.child("correct").getValue().toString()
                    val questionText=dataSnapshot.child("question").getValue().toString()
                    textView5.setText(questionText)
                    radioButton51.setText(option1Text)
                    radioButton52.setText(option2Text)
                    radioButton53.setText(option3Text)
                    radioButton54.setText(option4Text)
                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("QuizCategory5","Something Went Wrong!!")
                }
            })
        }
    }


    fun updateQuestion(){
        ref2=databaseRef.child("Technology").child(qlist.get(iterator))

        ref2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val option1Text=dataSnapshot.child("option1").getValue().toString()
                val option2Text=dataSnapshot.child("option2").getValue().toString()
                val option3Text=dataSnapshot.child("option3").getValue().toString()
                val option4Text=dataSnapshot.child("option4").getValue().toString()
                val questionText=dataSnapshot.child("question").getValue().toString()
                val correct=dataSnapshot.child("correct").getValue().toString()
                textView5.setText(questionText)
                radioButton51.setText(option1Text)
                radioButton52.setText(option2Text)
                radioButton53.setText(option3Text)
                radioButton54.setText(option4Text)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory5","Something went Wrong!!")
            }

        })

    }


    fun checkCorrectOrWrongForQ1(){
        ref=databaseRef.child("Technology").child("Ques1")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory5","Something went Wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val correctAns=dataSnapshot.child("correct").getValue().toString()
                if (radioButton51.isChecked) {
                    if(radioButton51.text.toString().equals(correctAns)){
                        correct++
                        radioButton51.setBackgroundColor(Color.GREEN)
                        score++

                    }
                    else{
                        wrong++
                        radioButton51.setBackgroundColor(Color.RED)
                    }

                }


                if (radioButton52.isChecked) {
                    if(radioButton52.text.toString().equals(correctAns)){
                        correct++
                        radioButton52.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton52.setBackgroundColor(Color.RED)
                    }

                }


                if (radioButton53.isChecked) {
                    if(radioButton53.text.toString().equals(correctAns)){
                        correct++
                        radioButton53.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton53.setBackgroundColor(Color.RED)
                    }
                }

                if (radioButton54.isChecked) {
                    if(radioButton54.text.toString().equals(correctAns)){
                        correct++
                        radioButton54.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton54.setBackgroundColor(Color.RED)
                    }
                }
            }
        })
    }


    fun CorrectForRestOfTheQuestions(){

        ref2=databaseRef.child("Technology").child(qlist.get(iterator))

        ref2.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory5","Something went wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val otherCorrectAns=dataSnapshot.child("correct").getValue().toString()
                if (radioButton51.isChecked) {
                    if(radioButton51.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton51.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton51.setBackgroundColor(Color.RED)
                    }

                }

                if (radioButton52.isChecked) {
                    if(radioButton52.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton52.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton52.setBackgroundColor(Color.RED)
                    }

                }

                if(radioButton53.isChecked) {
                    if(radioButton53.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton53.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton53.setBackgroundColor(Color.RED)
                    }
                }

                if (radioButton54.isChecked) {
                    if(radioButton54.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton54.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton54.setBackgroundColor(Color.RED)
                    }
                }
            }
        })
    }
}
