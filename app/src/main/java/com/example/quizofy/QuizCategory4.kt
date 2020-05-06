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
import kotlinx.android.synthetic.main.activity_quiz_category3.*
import kotlinx.android.synthetic.main.activity_quiz_category4.*
import kotlinx.android.synthetic.main.activity_quiz_category4.textView5
import kotlinx.android.synthetic.main.activity_result.*

class QuizCategory4 : AppCompatActivity() {
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
        setContentView(R.layout.activity_quiz_category4)
        Log.d("QuizCategory4","Quiz 4 oncreate called")
        qlist.add("Ques2")
        qlist.add("Ques3")
        qlist.add("Ques4")
        qlist.add("Ques5")

        radioButton41.isEnabled=false
        radioButton42.isEnabled=false
        radioButton43.isEnabled=false
        radioButton44.isEnabled=false

        databaseRef=FirebaseDatabase.getInstance().getReference("CATEGORIES")

        startTime4.setOnClickListener {
            getQuestion()
            startClicked4()
            Log.d("QuizCategory4","get question called and timer started")
            radioButton41.isEnabled=true
            radioButton42.isEnabled=true
            radioButton43.isEnabled=true
            radioButton44.isEnabled=true
            startTime4.visibility=View.INVISIBLE
            total++
        }
        submitButton4.setOnClickListener {
            resultKaIntent1()
            Toast.makeText(this,"Your Result",Toast.LENGTH_SHORT).show()
        }
    }

    fun startClicked4() {
        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1+(milisUntilFinished / 1000)))

                if (radioButton41.isChecked||radioButton42.isChecked||radioButton43.isChecked||radioButton44.isChecked) {
                    checkCorrectOrWrongForQ1()
                    radioButton41.isEnabled=false
                    radioButton42.isEnabled=false
                    radioButton43.isEnabled=false
                    radioButton44.isEnabled=false
                }
                else {
                    next4.setOnClickListener {
                        if (iterator <4) {
                            resetTimer()
                            updateQuestion()
                            radioButton41.isChecked=false
                            radioButton42.isChecked=false
                            radioButton43.isChecked=false
                            radioButton44.isChecked=false
                            radioButton41.isEnabled=true
                            radioButton42.isEnabled=true
                            radioButton43.isEnabled=true
                            radioButton44.isEnabled=true
                            radioButton41.setBackgroundColor(Color.TRANSPARENT)
                            radioButton42.setBackgroundColor(Color.TRANSPARENT)
                            radioButton43.setBackgroundColor(Color.TRANSPARENT)
                            radioButton44.setBackgroundColor(Color.TRANSPARENT)
                            if (radioButton41.isChecked||radioButton42.isChecked||radioButton43.isChecked||radioButton44.isChecked){
                                CorrectForRestOfTheQuestions()
                            }
                            next4.visibility = View.INVISIBLE
                            iterator++
                            next4.visibility = View.VISIBLE
                        }
                        else{
                            resultKaIntent1()
                        }
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

                    radioButton41.isEnabled=true
                    radioButton42.isEnabled=true
                    radioButton43.isEnabled=true
                    radioButton44.isEnabled=true
                    radioButton41.setBackgroundColor(Color.TRANSPARENT)
                    radioButton42.setBackgroundColor(Color.TRANSPARENT)
                    radioButton43.setBackgroundColor(Color.TRANSPARENT)
                    radioButton44.setBackgroundColor(Color.TRANSPARENT)
                    optionupdate()
                    resetoptions()
                    updateQuestion()
                    resetTimer()
                    if (radioButton41.isChecked||radioButton42.isChecked||radioButton43.isChecked||radioButton44.isChecked){
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
        if (radioButton41.isChecked || radioButton42.isChecked || radioButton43.isChecked || radioButton44.isChecked) {

            resetoptions()

        } else {
            resetTimer()
            updateQuestion()
        }
    }

    fun resetoptions(){
        if(radioButton41.isChecked){
            radioButton41.isChecked=false
        }
        else if (radioButton42.isChecked){
            radioButton42.isChecked=false
        }
        else if (radioButton43.isChecked){
            radioButton43.isChecked=false
        }
        else {
            radioButton44.isChecked=false
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
            Log.d("QuizCategory4","result activity started")
            startActivity(intent)
        } else
        {
            ref = databaseRef.child("History").child("Ques1")
            Log.d("QuizCategory4","firebase data get" )
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory4","on data change for started"+dataSnapshot.children.toString())
                    val option1Text=dataSnapshot.child("option1").getValue().toString()
                    val option2Text=dataSnapshot.child("option2").getValue().toString()
                    val option3Text=dataSnapshot.child("option3").getValue().toString()
                    val option4Text=dataSnapshot.child("option4").getValue().toString()
                    val correctAns=dataSnapshot.child("correct").getValue().toString()
                    val questionText=dataSnapshot.child("question").getValue().toString()
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


    fun updateQuestion(){
        ref2=databaseRef.child("History").child(qlist.get(iterator))

        ref2.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val option1Text=dataSnapshot.child("option1").getValue().toString()
                val option2Text=dataSnapshot.child("option2").getValue().toString()
                val option3Text=dataSnapshot.child("option3").getValue().toString()
                val option4Text=dataSnapshot.child("option4").getValue().toString()
                val questionText=dataSnapshot.child("question").getValue().toString()
                val correct=dataSnapshot.child("correct").getValue().toString()
                textView5.setText(questionText)
                radioButton41.setText(option1Text)
                radioButton42.setText(option2Text)
                radioButton43.setText(option3Text)
                radioButton44.setText(option4Text)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory6","Something went Wrong!!")
            }

        })

    }


    fun checkCorrectOrWrongForQ1(){
        ref=databaseRef.child("History").child("Ques1")
        ref.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory4","Something went Wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val correctAns=dataSnapshot.child("correct").getValue().toString()
                if (radioButton41.isChecked) {
                    if(radioButton41.text.toString().equals(correctAns)){
                        correct++
                        radioButton41.setBackgroundColor(Color.GREEN)
                        score++

                    }
                    else{
                        wrong++
                        radioButton41.setBackgroundColor(Color.RED)
                    }

                }


                if (radioButton42.isChecked) {
                    if(radioButton42.text.toString().equals(correctAns)){
                        correct++
                        radioButton42.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton42.setBackgroundColor(Color.RED)
                    }

                }


                if (radioButton43.isChecked) {
                    if(radioButton43.text.toString().equals(correctAns)){
                        correct++
                        radioButton43.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton43.setBackgroundColor(Color.RED)
                    }
                }

                if (radioButton44.isChecked) {
                    if(radioButton44.text.toString().equals(correctAns)){
                        correct++
                        radioButton44.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton44.setBackgroundColor(Color.RED)
                    }
                }
            }
        })
    }


    fun CorrectForRestOfTheQuestions(){

        ref2=databaseRef.child("History").child(qlist.get(iterator))

        ref2.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory4","Something went wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val otherCorrectAns=dataSnapshot.child("correct").getValue().toString()
                if (radioButton41.isChecked) {
                    if(radioButton41.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton41.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton41.setBackgroundColor(Color.RED)
                    }

                }

                if (radioButton32.isChecked) {
                    if(radioButton42.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton42.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton42.setBackgroundColor(Color.RED)
                    }

                }

                if(radioButton43.isChecked) {
                    if(radioButton43.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton43.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton43.setBackgroundColor(Color.RED)
                    }
                }

                if (radioButton44.isChecked) {
                    if(radioButton44.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton44.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton44.setBackgroundColor(Color.RED)
                    }
                }
            }
        })
    }
}
