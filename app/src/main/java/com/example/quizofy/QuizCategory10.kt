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
import kotlinx.android.synthetic.main.activity_quiz_category10.*
import kotlinx.android.synthetic.main.activity_quiz_category10.textView5
import kotlinx.android.synthetic.main.activity_quiz_category8.*
import kotlinx.android.synthetic.main.activity_quiz_category9.*
import kotlinx.android.synthetic.main.activity_result.*

class QuizCategory10 : AppCompatActivity() {
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
        setContentView(R.layout.activity_quiz_category10)
        Log.d("QuizCategory10","Quiz 10 oncreate called")
        qlist.add("Ques1")
        qlist.add("Ques2")
        qlist.add("Ques3")
        qlist.add("Ques4")
        qlist.add("Ques5")
        Toast.makeText(this,"Press Start Button",Toast.LENGTH_SHORT).show()
        radioButton01.isEnabled=false
        radioButton02.isEnabled=false
        radioButton03.isEnabled=false
        radioButton04.isEnabled=false
        next10.visibility=View.INVISIBLE

        databaseRef=FirebaseDatabase.getInstance().getReference("CATEGORIES")
        submitButton10.visibility=View.INVISIBLE

        startTime10.setOnClickListener {
            startClicked()
            getQuestion()
            Log.d("QuizCategory10","get question called and timer started")
            radioButton01.isEnabled=true
            radioButton02.isEnabled=true
            radioButton03.isEnabled=true
            radioButton04.isEnabled=true
            next10.visibility=View.VISIBLE
            startTime10.visibility=View.INVISIBLE
            total++
        }

        next10.setOnClickListener {
            if (iterator<5) {
                radioButton01.isEnabled = true
                radioButton02.isEnabled = true
                radioButton03.isEnabled = true
                radioButton04.isEnabled = true
                radioButton01.setBackgroundColor(Color.TRANSPARENT)
                radioButton02.setBackgroundColor(Color.TRANSPARENT)
                radioButton03.setBackgroundColor(Color.TRANSPARENT)
                radioButton04.setBackgroundColor(Color.TRANSPARENT)
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

        submitButton10.setOnClickListener {
            resultKaIntent()
            Toast.makeText(this, "Your Result", Toast.LENGTH_SHORT).show()
        }
    }


    fun startClicked() {
        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("Time Left:"+"00:" + (1 + (milisUntilFinished / 1000)))
                if (radioButton01.isChecked || radioButton02.isChecked || radioButton03.isChecked || radioButton04.isChecked){
                    radioButton01.isEnabled=false
                    radioButton02.isEnabled=false
                    radioButton03.isEnabled=false
                    radioButton04.isEnabled=false
                    checkCorrectOrWrongForQues()
                }

            }
            override fun onFinish() {
                textView3.text = "Time Over!!"
                radioButton01.isEnabled = false
                radioButton02.isEnabled = false
                radioButton03.isEnabled = false
                radioButton04.isEnabled = false
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
    fun resultKaIntent(){
        val intent =Intent(this,ResultActivity::class.java)
        intent.putExtra("correct",correct.toString())
        intent.putExtra("Wrong",wrong.toString())
        intent.putExtra("score",score.toString())
        startActivity(intent)
    }


    fun getQuestion(){
        if (total >=5) {
            submitButton10.visibility=View.VISIBLE
            next10.visibility=View.INVISIBLE
            radioButton01.isEnabled=false
            radioButton02.isEnabled=false
            radioButton03.isEnabled=false
            radioButton04.isEnabled=false
            textView3.visibility=View.INVISIBLE
            Toast.makeText(this,"Press Submit button to view the result",Toast.LENGTH_SHORT).show()
            submitButton10.setOnClickListener {
                resultKaIntent()
            }
        } else
        {
            ref = databaseRef.child("Politics")
                .child(qlist.get(iterator))
            Log.d("QuizCategory10","firebase data get" )
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory10","on data change for started"+dataSnapshot.children.toString())
                    val option1Text=dataSnapshot.child("option1").getValue().toString()
                    val option2Text=dataSnapshot.child("option2").getValue().toString()
                    val option3Text=dataSnapshot.child("option3").getValue().toString()
                    val option4Text=dataSnapshot.child("option4").getValue().toString()
                    val questionText=dataSnapshot.child("question").getValue().toString()
                    val quesNo=total.toString()
                    textView5.setText(quesNo+". "+questionText)
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

    fun checkCorrectOrWrongForQues(){
        ref = databaseRef.child("Politics").child(qlist.get(iterator))
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory10", "Something went Wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val correctAns = dataSnapshot.child("correct").getValue().toString()
                if (radioButton01.isChecked ) {
                    if (radioButton01.text.toString().equals(correctAns)){
                        correct++
                        score++
                        radioButton01.setBackgroundColor(Color.GREEN)
                        radioButton01.isChecked=false
                    } else {
                        wrong=total-correct
                        radioButton01.setBackgroundColor(Color.RED)
                        radioButton01.isChecked=false
                    }
                }

                if (radioButton02.isChecked) {
                    if (radioButton02.text.toString().equals(correctAns)) {
                        correct++
                        radioButton02.setBackgroundColor(Color.GREEN)
                        score++
                        radioButton02.isChecked=false
                    } else {
                        wrong=total-correct
                        radioButton02.setBackgroundColor(Color.RED)
                        radioButton03.isChecked=false
                    }
                }

                if (radioButton03.isChecked ) {
                    if (radioButton03.text.toString().equals(correctAns)) {
                        correct++
                        radioButton03.setBackgroundColor(Color.GREEN)
                        score++
                        radioButton03.isChecked=false
                    }
                    else {
                        wrong=total-correct
                        radioButton03.setBackgroundColor(Color.RED)
                        radioButton03.isChecked=false
                    }
                }

                if (radioButton04.isChecked ) {
                    if (radioButton04.text.toString().equals(correctAns)) {
                        correct++
                        radioButton04.setBackgroundColor(Color.GREEN)
                        score++
                        radioButton04.isChecked=false
                    } else {
                        wrong=total-correct
                        radioButton04.setBackgroundColor(Color.RED)
                        radioButton04.isChecked=false
                    }
                }
            }
        })
    }
}