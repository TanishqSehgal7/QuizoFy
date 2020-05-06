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
import kotlinx.android.synthetic.main.activity_quiz_category6.*
import kotlinx.android.synthetic.main.activity_quiz_category7.*
import kotlinx.android.synthetic.main.activity_quiz_category7.textView5
import kotlinx.android.synthetic.main.activity_result.*

class QuizCategory7 : AppCompatActivity() {
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
        setContentView(R.layout.activity_quiz_category7)
        Log.d("QuizCategory7","Quiz 7 oncreate called")
        qlist.add("Ques2")
        qlist.add("Ques3")
        qlist.add("Ques4")
        qlist.add("Ques5")

        radioButton71.isEnabled=false
        radioButton72.isEnabled=false
        radioButton73.isEnabled=false
        radioButton74.isEnabled=false

        databaseRef= FirebaseDatabase.getInstance().getReference("CATEGORIES")

        startTime7.setOnClickListener {
            getQuestion()
            startClicked7()
            Log.d("QuizCategory8","get question called and timer started")
            radioButton71.isEnabled=true
            radioButton72.isEnabled=true
            radioButton73.isEnabled=true
            radioButton74.isEnabled=true
            startTime7.visibility=View.INVISIBLE
            total++
        }
        submitButton7.setOnClickListener {
            resultKaIntent1()
            Toast.makeText(this,"Your Result",Toast.LENGTH_SHORT).show()
        }
    }

    fun startClicked7() {
        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1+(milisUntilFinished / 1000)))

                if (radioButton71.isChecked||radioButton72.isChecked||radioButton73.isChecked||radioButton74.isChecked) {
                    checkCorrectOrWrongForQ1()
                    radioButton71.isEnabled=false
                    radioButton72.isEnabled=false
                    radioButton73.isEnabled=false
                    radioButton74.isEnabled=false
                }
                else {
                    next7.setOnClickListener {
                        if (iterator <4) {
                            resetTimer()
                            updateQuestion()
                            radioButton71.isChecked=false
                            radioButton72.isChecked=false
                            radioButton73.isChecked=false
                            radioButton74.isChecked=false
                            radioButton71.isEnabled=true
                            radioButton72.isEnabled=true
                            radioButton73.isEnabled=true
                            radioButton74.isEnabled=true
                            radioButton71.setBackgroundColor(Color.TRANSPARENT)
                            radioButton72.setBackgroundColor(Color.TRANSPARENT)
                            radioButton73.setBackgroundColor(Color.TRANSPARENT)
                            radioButton74.setBackgroundColor(Color.TRANSPARENT)
                            if (radioButton71.isChecked||radioButton72.isChecked||radioButton73.isChecked||radioButton74.isChecked){
                                CorrectForRestOfTheQuestions()
                            }
                            next7.visibility = View.INVISIBLE
                            iterator++
                            next7.visibility = View.VISIBLE
                        }
                        else{
                            resultKaIntent1()
                        }
                    }

                }
            }
            override fun onFinish() {
                textView3.text = "Time Over!!"
                radioButton71.isEnabled=false
                radioButton72.isEnabled=false
                radioButton73.isEnabled=false
                radioButton74.isEnabled=false
                next7.setOnClickListener {

                    radioButton71.isEnabled=true
                    radioButton72.isEnabled=true
                    radioButton73.isEnabled=true
                    radioButton74.isEnabled=true
                    radioButton71.setBackgroundColor(Color.TRANSPARENT)
                    radioButton72.setBackgroundColor(Color.TRANSPARENT)
                    radioButton73.setBackgroundColor(Color.TRANSPARENT)
                    radioButton74.setBackgroundColor(Color.TRANSPARENT)
                    optionupdate()
                    resetoptions()
                    updateQuestion()
                    resetTimer()
                    if (radioButton71.isChecked||radioButton72.isChecked||radioButton73.isChecked||radioButton74.isChecked){
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
        if (radioButton71.isChecked || radioButton72.isChecked || radioButton73.isChecked || radioButton74.isChecked) {

            resetoptions()

        } else {
            resetTimer()
            updateQuestion()
        }
    }

    fun resetoptions(){
        if(radioButton71.isChecked){
            radioButton71.isChecked=false
        }
        else if (radioButton72.isChecked){
            radioButton72.isChecked=false
        }
        else if (radioButton73.isChecked){
            radioButton73.isChecked=false
        }
        else {
            radioButton74.isChecked=false
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
            Log.d("QuizCategory7","result activity started")
            startActivity(intent)
        } else
        {
            ref = databaseRef.child("Entertainment").child("Ques1")
            Log.d("QuizCategory7","firebase data get" )
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory7","on data change for started"+dataSnapshot.children.toString())
                    val option1Text=dataSnapshot.child("option1").getValue().toString()
                    val option2Text=dataSnapshot.child("option2").getValue().toString()
                    val option3Text=dataSnapshot.child("option3").getValue().toString()
                    val option4Text=dataSnapshot.child("option4").getValue().toString()
                    val correctAns=dataSnapshot.child("correct").getValue().toString()
                    val questionText=dataSnapshot.child("question").getValue().toString()
                    textView5.setText(questionText)
                    radioButton71.setText(option1Text)
                    radioButton72.setText(option2Text)
                    radioButton73.setText(option3Text)
                    radioButton74.setText(option4Text)
                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("QuizCategory7","Something Went Wrong!!")
                }
            })
        }
    }


    fun updateQuestion(){
        ref2=databaseRef.child("Entertainment").child(qlist.get(iterator))

        ref2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val option1Text=dataSnapshot.child("option1").getValue().toString()
                val option2Text=dataSnapshot.child("option2").getValue().toString()
                val option3Text=dataSnapshot.child("option3").getValue().toString()
                val option4Text=dataSnapshot.child("option4").getValue().toString()
                val questionText=dataSnapshot.child("question").getValue().toString()
                val correct=dataSnapshot.child("correct").getValue().toString()
                textView5.setText(questionText)
                radioButton71.setText(option1Text)
                radioButton72.setText(option2Text)
                radioButton73.setText(option3Text)
                radioButton74.setText(option4Text)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory7","Something went Wrong!!")
            }

        })

    }


    fun checkCorrectOrWrongForQ1(){
        ref=databaseRef.child("Entertainment").child("Ques1")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory7","Something went Wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val correctAns=dataSnapshot.child("correct").getValue().toString()
                if (radioButton71.isChecked) {
                    if(radioButton71.text.toString().equals(correctAns)){
                        correct++
                        radioButton71.setBackgroundColor(Color.GREEN)
                        score++

                    }
                    else{
                        wrong++
                        radioButton71.setBackgroundColor(Color.RED)
                    }

                }


                if (radioButton72.isChecked) {
                    if(radioButton72.text.toString().equals(correctAns)){
                        correct++
                        radioButton72.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton72.setBackgroundColor(Color.RED)
                    }

                }


                if (radioButton73.isChecked) {
                    if(radioButton73.text.toString().equals(correctAns)){
                        correct++
                        radioButton73.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton73.setBackgroundColor(Color.RED)
                    }
                }

                if (radioButton74.isChecked) {
                    if(radioButton74.text.toString().equals(correctAns)){
                        correct++
                        radioButton74.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton74.setBackgroundColor(Color.RED)
                    }
                }
            }
        })
    }


    fun CorrectForRestOfTheQuestions(){

        ref2=databaseRef.child("Entertainment").child(qlist.get(iterator))

        ref2.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory7","Something went wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val otherCorrectAns=dataSnapshot.child("correct").getValue().toString()
                if (radioButton71.isChecked) {
                    if(radioButton71.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton71.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton71.setBackgroundColor(Color.RED)
                    }

                }

                if (radioButton72.isChecked) {
                    if(radioButton72.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton72.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton72.setBackgroundColor(Color.RED)
                    }

                }

                if(radioButton73.isChecked) {
                    if(radioButton73.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton73.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton73.setBackgroundColor(Color.RED)
                    }
                }

                if (radioButton74.isChecked) {
                    if(radioButton74.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton74.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton74.setBackgroundColor(Color.RED)
                    }
                }
            }
        })
    }
}
