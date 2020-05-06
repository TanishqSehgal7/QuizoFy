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
import kotlinx.android.synthetic.main.activity_quiz_category5.*
import kotlinx.android.synthetic.main.activity_quiz_category6.*
import kotlinx.android.synthetic.main.activity_quiz_category6.textView5
import kotlinx.android.synthetic.main.activity_result.*

class QuizCategory6 : AppCompatActivity() {
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
        setContentView(R.layout.activity_quiz_category6)
        Log.d("QuizCategory6","Quiz 6 oncreate called")
        qlist.add("Ques2")
        qlist.add("Ques3")
        qlist.add("Ques4")
        qlist.add("Ques5")

        radioButton61.isEnabled=false
        radioButton62.isEnabled=false
        radioButton63.isEnabled=false
        radioButton64.isEnabled=false

        databaseRef= FirebaseDatabase.getInstance().getReference("CATEGORIES")

        startTime6.setOnClickListener {
            getQuestion()
            startClicked6()
            Log.d("QuizCategory6","get question called and timer started")
            radioButton61.isEnabled=true
            radioButton62.isEnabled=true
            radioButton63.isEnabled=true
            radioButton64.isEnabled=true
            startTime6.visibility=View.INVISIBLE
            total++
        }
        submitButton6.setOnClickListener {
            resultKaIntent1()
            Toast.makeText(this,"Your Result",Toast.LENGTH_SHORT).show()
        }
    }

    fun startClicked6() {
        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1+(milisUntilFinished / 1000)))

                if (radioButton61.isChecked||radioButton62.isChecked||radioButton63.isChecked||radioButton64.isChecked) {
                    checkCorrectOrWrongForQ1()
                    radioButton61.isEnabled=false
                    radioButton62.isEnabled=false
                    radioButton63.isEnabled=false
                    radioButton64.isEnabled=false
                }
                else {
                    next6.setOnClickListener {
                        if (iterator <4) {
                            resetTimer()
                            updateQuestion()
                            radioButton61.isChecked=false
                            radioButton62.isChecked=false
                            radioButton63.isChecked=false
                            radioButton64.isChecked=false
                            radioButton61.isEnabled=true
                            radioButton62.isEnabled=true
                            radioButton63.isEnabled=true
                            radioButton64.isEnabled=true
                            radioButton61.setBackgroundColor(Color.TRANSPARENT)
                            radioButton62.setBackgroundColor(Color.TRANSPARENT)
                            radioButton63.setBackgroundColor(Color.TRANSPARENT)
                            radioButton64.setBackgroundColor(Color.TRANSPARENT)
                            if (radioButton61.isChecked||radioButton62.isChecked||radioButton63.isChecked||radioButton64.isChecked){
                                CorrectForRestOfTheQuestions()
                            }
                            next6.visibility = View.INVISIBLE
                            iterator++
                            next6.visibility = View.VISIBLE
                        }
                        else{
                            resultKaIntent1()
                        }
                    }

                }
            }
            override fun onFinish() {
                textView3.text = "Time Over!!"
                radioButton61.isEnabled=false
                radioButton62.isEnabled=false
                radioButton63.isEnabled=false
                radioButton64.isEnabled=false
                next6.setOnClickListener {

                    radioButton61.isEnabled=true
                    radioButton62.isEnabled=true
                    radioButton63.isEnabled=true
                    radioButton64.isEnabled=true
                    radioButton61.setBackgroundColor(Color.TRANSPARENT)
                    radioButton62.setBackgroundColor(Color.TRANSPARENT)
                    radioButton63.setBackgroundColor(Color.TRANSPARENT)
                    radioButton64.setBackgroundColor(Color.TRANSPARENT)
                    optionupdate()
                    resetoptions()
                    updateQuestion()
                    resetTimer()
                    if (radioButton61.isChecked||radioButton62.isChecked||radioButton63.isChecked||radioButton64.isChecked){
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
        if (radioButton61.isChecked || radioButton62.isChecked || radioButton63.isChecked || radioButton64.isChecked) {

            resetoptions()

        } else {
            resetTimer()
            updateQuestion()
        }
    }

    fun resetoptions(){
        if(radioButton61.isChecked){
            radioButton61.isChecked=false
        }
        else if (radioButton62.isChecked){
            radioButton62.isChecked=false
        }
        else if (radioButton63.isChecked){
            radioButton63.isChecked=false
        }
        else {
            radioButton64.isChecked=false
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
            Log.d("QuizCategory6","result activity started")
            startActivity(intent)
        } else
        {
            ref = databaseRef.child("Sports").child("Ques1")
            Log.d("QuizCategory6","firebase data get" )
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory6","on data change for started"+dataSnapshot.children.toString())
                    val option1Text=dataSnapshot.child("option1").getValue().toString()
                    val option2Text=dataSnapshot.child("option2").getValue().toString()
                    val option3Text=dataSnapshot.child("option3").getValue().toString()
                    val option4Text=dataSnapshot.child("option4").getValue().toString()
                    val correctAns=dataSnapshot.child("correct").getValue().toString()
                    val questionText=dataSnapshot.child("question").getValue().toString()
                    textView5.setText(questionText)
                    radioButton61.setText(option1Text)
                    radioButton62.setText(option2Text)
                    radioButton63.setText(option3Text)
                    radioButton64.setText(option4Text)
                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("QuizCategory6","Something Went Wrong!!")
                }
            })
        }
    }


    fun updateQuestion(){
        ref2=databaseRef.child("Sports").child(qlist.get(iterator))

        ref2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val option1Text=dataSnapshot.child("option1").getValue().toString()
                val option2Text=dataSnapshot.child("option2").getValue().toString()
                val option3Text=dataSnapshot.child("option3").getValue().toString()
                val option4Text=dataSnapshot.child("option4").getValue().toString()
                val questionText=dataSnapshot.child("question").getValue().toString()
                val correct=dataSnapshot.child("correct").getValue().toString()
                textView5.setText(questionText)
                radioButton61.setText(option1Text)
                radioButton62.setText(option2Text)
                radioButton63.setText(option3Text)
                radioButton64.setText(option4Text)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory6","Something went Wrong!!")
            }

        })

    }


    fun checkCorrectOrWrongForQ1(){
        ref=databaseRef.child("Sports").child("Ques1")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory6","Something went Wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val correctAns=dataSnapshot.child("correct").getValue().toString()
                if (radioButton61.isChecked) {
                    if(radioButton61.text.toString().equals(correctAns)){
                        correct++
                        radioButton61.setBackgroundColor(Color.GREEN)
                        score++

                    }
                    else{
                        wrong++
                        radioButton61.setBackgroundColor(Color.RED)
                    }

                }


                if (radioButton62.isChecked) {
                    if(radioButton62.text.toString().equals(correctAns)){
                        correct++
                        radioButton62.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton62.setBackgroundColor(Color.RED)
                    }

                }


                if (radioButton63.isChecked) {
                    if(radioButton63.text.toString().equals(correctAns)){
                        correct++
                        radioButton63.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton63.setBackgroundColor(Color.RED)
                    }
                }

                if (radioButton64.isChecked) {
                    if(radioButton64.text.toString().equals(correctAns)){
                        correct++
                        radioButton64.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton64.setBackgroundColor(Color.RED)
                    }
                }
            }
        })
    }


    fun CorrectForRestOfTheQuestions(){

        ref2=databaseRef.child("Sports").child(qlist.get(iterator))

        ref2.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory6","Something went wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val otherCorrectAns=dataSnapshot.child("correct").getValue().toString()
                if (radioButton61.isChecked) {
                    if(radioButton61.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton61.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton61.setBackgroundColor(Color.RED)
                    }

                }

                if (radioButton62.isChecked) {
                    if(radioButton62.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton62.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton62.setBackgroundColor(Color.RED)
                    }

                }

                if(radioButton63.isChecked) {
                    if(radioButton63.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton63.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton63.setBackgroundColor(Color.RED)
                    }
                }

                if (radioButton64.isChecked) {
                    if(radioButton64.text.toString().equals(otherCorrectAns)){
                        correct++
                        radioButton64.setBackgroundColor(Color.GREEN)
                        score++
                    }
                    else{
                        wrong++
                        radioButton64.setBackgroundColor(Color.RED)
                    }
                }
            }
        })
    }
}