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
import kotlinx.android.synthetic.main.activity_quiz_category8.*
import kotlinx.android.synthetic.main.activity_quiz_category9.*
import kotlinx.android.synthetic.main.activity_quiz_category9.textView5
import kotlinx.android.synthetic.main.activity_result.*

class QuizCategory9 : AppCompatActivity() {
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
        setContentView(R.layout.activity_quiz_category9)
        Log.d("QuizCategory9","Quiz 9 oncreate called")
        qlist.add("Ques2")
        qlist.add("Ques3")
        qlist.add("Ques4")
        qlist.add("Ques5")

        radioButton91.isEnabled=false
        radioButton92.isEnabled=false
        radioButton93.isEnabled=false
        radioButton94.isEnabled=false

        databaseRef= FirebaseDatabase.getInstance().getReference("CATEGORIES")

        startTime9.setOnClickListener {
            getQuestion()
            startClicked9()
            Log.d("QuizCategory9","get question called and timer started")
            radioButton91.isEnabled=true
            radioButton92.isEnabled=true
            radioButton93.isEnabled=true
            radioButton94.isEnabled=true
            startTime9.visibility=View.INVISIBLE
            total++
        }
        submitButton9.setOnClickListener {
            resultKaIntent1()
            Toast.makeText(this,"Your Result",Toast.LENGTH_SHORT).show()
        }
    }

    fun startClicked9() {
        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1+(milisUntilFinished / 1000)))

                if (radioButton91.isChecked||radioButton92.isChecked||radioButton93.isChecked||radioButton94.isChecked) {
                    checkCorrectOrWrongForQ1()
                    radioButton91.isEnabled=false
                    radioButton92.isEnabled=false
                    radioButton93.isEnabled=false
                    radioButton94.isEnabled=false
                }
                else {
                    next9.setOnClickListener {
                        if (iterator <4) {
                            resetTimer()
                            updateQuestion()
                            radioButton91.isChecked=false
                            radioButton92.isChecked=false
                            radioButton93.isChecked=false
                            radioButton94.isChecked=false
                            radioButton91.isEnabled=true
                            radioButton92.isEnabled=true
                            radioButton93.isEnabled=true
                            radioButton94.isEnabled=true
                            radioButton91.setBackgroundColor(Color.TRANSPARENT)
                            radioButton92.setBackgroundColor(Color.TRANSPARENT)
                            radioButton93.setBackgroundColor(Color.TRANSPARENT)
                            radioButton94.setBackgroundColor(Color.TRANSPARENT)
                            if (radioButton91.isChecked||radioButton92.isChecked||radioButton93.isChecked||radioButton94.isChecked){
                                CorrectForRestOfTheQuestions()
                            }
                            next9.visibility = View.INVISIBLE
                            iterator++
                            next9.visibility = View.VISIBLE
                        }
                        else{
                            resultKaIntent1()
                        }
                    }

                }
            }
            override fun onFinish() {
                textView3.text = "Time Over!!"
                radioButton91.isEnabled=false
                radioButton92.isEnabled=false
                radioButton93.isEnabled=false
                radioButton94.isEnabled=false
                next9.setOnClickListener {

                    radioButton91.isEnabled=true
                    radioButton92.isEnabled=true
                    radioButton93.isEnabled=true
                    radioButton94.isEnabled=true
                    radioButton91.setBackgroundColor(Color.TRANSPARENT)
                    radioButton92.setBackgroundColor(Color.TRANSPARENT)
                    radioButton93.setBackgroundColor(Color.TRANSPARENT)
                    radioButton94.setBackgroundColor(Color.TRANSPARENT)
                    optionupdate()
                    resetoptions()
                    updateQuestion()
                    resetTimer()
                    if (radioButton91.isChecked||radioButton92.isChecked||radioButton93.isChecked||radioButton94.isChecked){
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
        if (radioButton91.isChecked || radioButton92.isChecked || radioButton93.isChecked || radioButton94.isChecked) {

            resetoptions()

        } else {
            resetTimer()
            updateQuestion()
        }
    }

    fun resetoptions(){
        if(radioButton91.isChecked){
            radioButton91.isChecked=false
        }
        else if (radioButton92.isChecked){
            radioButton92.isChecked=false
        }
        else if (radioButton93.isChecked){
            radioButton93.isChecked=false
        }
        else {
            radioButton94.isChecked=false
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
            Log.d("QuizCategory9","result activity started")
            startActivity(intent)
        } else
        {
            ref = databaseRef.child("Sneaker Culture").child("Ques1")
            Log.d("QuizCategory9","firebase data get" )
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory9","on data change for started"+dataSnapshot.children.toString())
                    val option1Text=dataSnapshot.child("option1").getValue().toString()
                    val option2Text=dataSnapshot.child("option2").getValue().toString()
                    val option3Text=dataSnapshot.child("option3").getValue().toString()
                    val option4Text=dataSnapshot.child("option4").getValue().toString()
                    val correctAns=dataSnapshot.child("correct").getValue().toString()
                    val questionText=dataSnapshot.child("question").getValue().toString()
                    textView5.setText(questionText)
                    radioButton91.setText(option1Text)
                    radioButton92.setText(option2Text)
                    radioButton93.setText(option3Text)
                    radioButton94.setText(option4Text)
                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("QuizCategory9","Something Went Wrong!!")
                }
            })
        }
    }


    fun updateQuestion(){
        ref2=databaseRef.child("Sneaker Culture").child(qlist.get(iterator))

        ref2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val option1Text=dataSnapshot.child("option1").getValue().toString()
                val option2Text=dataSnapshot.child("option2").getValue().toString()
                val option3Text=dataSnapshot.child("option3").getValue().toString()
                val option4Text=dataSnapshot.child("option4").getValue().toString()
                val questionText=dataSnapshot.child("question").getValue().toString()
                val correct=dataSnapshot.child("correct").getValue().toString()
                textView5.setText(questionText)
                radioButton91.setText(option1Text)
                radioButton92.setText(option2Text)
                radioButton93.setText(option3Text)
                radioButton94.setText(option4Text)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory9","Something went Wrong!!")
            }

        })

    }


    fun checkCorrectOrWrongForQ1(){
        ref=databaseRef.child("Sneaker Culture").child("Ques1")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory9","Something went Wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val correctAns=dataSnapshot.child("correct").getValue().toString()
                if (radioButton91.isChecked) {
                    if(radioButton91.text.toString().equals(correctAns)){
                        correct++
                        radioButton91.setBackgroundColor(Color.GREEN)
                        score++

                    }
                    else{
                        wrong++
                        radioButton91.setBackgroundColor(Color.RED)
                    }

                }


                if (radioButton92.isChecked) {
                    if(radioButton92.text.toString().equals(correctAns)){
                        correct++
                        radioButton92.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton92.setBackgroundColor(Color.RED)
                    }

                }


                if (radioButton93.isChecked) {
                    if(radioButton93.text.toString().equals(correctAns)){
                        correct++
                        radioButton93.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton93.setBackgroundColor(Color.RED)
                    }
                }

                if (radioButton94.isChecked) {
                    if(radioButton94.text.toString().equals(correctAns)){
                        correct++
                        radioButton94.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton94.setBackgroundColor(Color.RED)
                    }
                }
            }
        })
    }


    fun CorrectForRestOfTheQuestions(){

        ref2=databaseRef.child("Sneaker Culture").child(qlist.get(iterator))

        ref2.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory9","Something went wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val otherCorrectAns=dataSnapshot.child("correct").getValue().toString()
                if (radioButton91.isChecked) {
                    if(radioButton91.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton91.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton91.setBackgroundColor(Color.RED)
                    }

                }

                if (radioButton92.isChecked) {
                    if(radioButton92.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton92.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton92.setBackgroundColor(Color.RED)
                    }

                }

                if(radioButton93.isChecked) {
                    if(radioButton93.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton93.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton93.setBackgroundColor(Color.RED)
                    }
                }

                if (radioButton94.isChecked) {
                    if(radioButton94.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton94.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton94.setBackgroundColor(Color.RED)
                    }
                }
            }
        })
    }
}
