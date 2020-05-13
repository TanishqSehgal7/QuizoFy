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
import kotlinx.android.synthetic.main.activity_quiz_category4.*
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
    lateinit var ref:DatabaseReference
    private lateinit var databaseRef:DatabaseReference
    lateinit var timeCount :CountDownTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category6)
        Log.d("QuizCategory6","Quiz 6 oncreate called")
        qlist.add("Ques1")
        qlist.add("Ques2")
        qlist.add("Ques3")
        qlist.add("Ques4")
        qlist.add("Ques5")
        Toast.makeText(this,"Press Start Button",Toast.LENGTH_SHORT).show()
        radioButton61.isEnabled=false
        radioButton62.isEnabled=false
        radioButton63.isEnabled=false
        radioButton64.isEnabled=false

        databaseRef=FirebaseDatabase.getInstance().getReference("CATEGORIES")

        startTime6.setOnClickListener {
            startClicked()
            getQuestion()
            Log.d("QuizCategory6","get question called and timer started")
            radioButton61.isEnabled=true
            radioButton62.isEnabled=true
            radioButton63.isEnabled=true
            radioButton64.isEnabled=true
            startTime6.visibility=View.INVISIBLE
            total++
        }

        next6.setOnClickListener {
            if (iterator<5) {
                radioButton61.isEnabled = true
                radioButton62.isEnabled = true
                radioButton63.isEnabled = true
                radioButton64.isEnabled = true
                radioButton61.setBackgroundColor(Color.TRANSPARENT)
                radioButton62.setBackgroundColor(Color.TRANSPARENT)
                radioButton63.setBackgroundColor(Color.TRANSPARENT)
                radioButton64.setBackgroundColor(Color.TRANSPARENT)
                iterator++
                getQuestion()
                resetoptions()
                optionupdate()
                resetTimer()
                total++
            }
            else{
                resultKaIntent()
            }

        }

        submitButton6.setOnClickListener {
            resultKaIntent()
            Toast.makeText(this, "Your Result", Toast.LENGTH_SHORT).show()
        }
    }


    fun startClicked() {
        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1 + (milisUntilFinished / 1000)))
                if (radioButton61.isChecked || radioButton62.isChecked || radioButton63.isChecked || radioButton64.isChecked){
                    radioButton61.isEnabled=false
                    radioButton62.isEnabled=false
                    radioButton63.isEnabled=false
                    radioButton64.isEnabled=false
                    checkCorrectOrWrongForQues()
                }

            }
            override fun onFinish() {
                textView3.text = "Time Over!!"
                radioButton61.isEnabled = false
                radioButton62.isEnabled = false
                radioButton63.isEnabled = false
                radioButton64.isEnabled = false
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
    fun resultKaIntent(){
        val intent =Intent(this,ResultActivity::class.java)
        intent.putExtra("correct",correct)
        intent.putExtra("Wrong",wrong)
        intent.putExtra("score",score)
        startActivity(intent)
        correctAns.setText(correct)
        wrongAns.setText(wrong)
        ScoreTotal.setText(score)
    }


    fun getQuestion(){
        if (total > 4) {
            resultKaIntent()
        } else
        {
            ref = databaseRef.child("Sports")
                .child(qlist.get(iterator))
            Log.d("QuizCategory6","firebase data get" )
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory6","on data change for started"+dataSnapshot.children.toString())
                    val option1Text=dataSnapshot.child("option1").getValue().toString()
                    val option2Text=dataSnapshot.child("option2").getValue().toString()
                    val option3Text=dataSnapshot.child("option3").getValue().toString()
                    val option4Text=dataSnapshot.child("option4").getValue().toString()
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


    fun checkCorrectOrWrongForQues(){
        ref = databaseRef.child("Sports").child(qlist.get(iterator))
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory6", "Something went Wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val correctAns = dataSnapshot.child("correct").getValue().toString()
                if (radioButton61.isChecked  && radioButton61.text.toString().equals(correctAns))
                {
                    correct++
                    score++
                    radioButton61.setBackgroundColor(Color.GREEN)
                } else {
                    wrong++
                    radioButton61.setBackgroundColor(Color.RED)
                }

                if (radioButton62.isChecked && radioButton62.text.toString().equals(correctAns)) {
                    correct++
                    radioButton62.setBackgroundColor(Color.GREEN)
                    score++
                } else {
                    wrong++
                    radioButton62.setBackgroundColor(Color.RED)
                }


                if (radioButton63.isChecked && radioButton63.text.toString().equals(correctAns)) {
                    correct++
                    radioButton63.setBackgroundColor(Color.GREEN)
                    score++
                } else {
                    wrong++
                    radioButton63.setBackgroundColor(Color.RED)
                }

                if (radioButton64.isChecked && radioButton64.text.toString().equals(correctAns)) {
                    correct++
                    radioButton64.setBackgroundColor(Color.GREEN)
                    score++
                } else {
                    wrong++
                    radioButton64.setBackgroundColor(Color.RED)
                }
            }
        })
    }
}