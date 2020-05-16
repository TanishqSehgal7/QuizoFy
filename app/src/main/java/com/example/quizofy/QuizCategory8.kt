package com.example.quizofy

import android.content.Context
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
import kotlinx.android.synthetic.main.activity_quiz_category7.*
import kotlinx.android.synthetic.main.activity_quiz_category8.*
import kotlinx.android.synthetic.main.activity_quiz_category8.textView5
import kotlinx.android.synthetic.main.activity_quiz_category9.*
import kotlinx.android.synthetic.main.activity_result.*

class QuizCategory8 : AppCompatActivity() {
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
        setContentView(R.layout.activity_quiz_category8)
        Log.d("QuizCategory8","Quiz 8 oncreate called")
        qlist.add("Ques1")
        qlist.add("Ques2")
        qlist.add("Ques3")
        qlist.add("Ques4")
        qlist.add("Ques5")
        Toast.makeText(this,"Press Start Button",Toast.LENGTH_SHORT).show()
        radioButton81.isEnabled=false
        radioButton82.isEnabled=false
        radioButton83.isEnabled=false
        radioButton84.isEnabled=false
        next8.visibility=View.INVISIBLE

        databaseRef=FirebaseDatabase.getInstance().getReference("CATEGORIES")
        submitButton8.visibility=View.INVISIBLE

        startTime8.setOnClickListener {
            startClicked()
            getQuestion()
            Log.d("QuizCategory8","get question called and timer started")
            radioButton81.isEnabled=true
            radioButton82.isEnabled=true
            radioButton83.isEnabled=true
            radioButton84.isEnabled=true
            next8.visibility=View.VISIBLE
            startTime8.visibility=View.INVISIBLE
            total++
        }

        next8.setOnClickListener {
            if (iterator<5) {
                radioButton81.isEnabled = true
                radioButton82.isEnabled = true
                radioButton83.isEnabled = true
                radioButton84.isEnabled = true
                radioButton81.setBackgroundColor(Color.TRANSPARENT)
                radioButton82.setBackgroundColor(Color.TRANSPARENT)
                radioButton83.setBackgroundColor(Color.TRANSPARENT)
                radioButton84.setBackgroundColor(Color.TRANSPARENT)
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

        submitButton8.setOnClickListener {
            resultKaIntent()
            Toast.makeText(this, "Your Result", Toast.LENGTH_SHORT).show()
        }
    }


    fun startClicked() {
        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("Time Left:"+"00:" + (1 + (milisUntilFinished / 1000)))
                if (radioButton81.isChecked || radioButton82.isChecked || radioButton83.isChecked || radioButton84.isChecked){
                    radioButton81.isEnabled=false
                    radioButton82.isEnabled=false
                    radioButton83.isEnabled=false
                    radioButton84.isEnabled=false
                    checkCorrectOrWrongForQues()
                }

            }
            override fun onFinish() {
                textView3.text = "Time Over!!"
                radioButton81.isEnabled = false
                radioButton82.isEnabled = false
                radioButton83.isEnabled = false
                radioButton84.isEnabled = false
            }
        }.start()

    }

    fun resetTimer(){
        timeCount.start()
    }

    fun optionupdate() {
        if (radioButton81.isChecked || radioButton82.isChecked || radioButton83.isChecked || radioButton84.isChecked) {
            resetoptions()
        } else {
            resetTimer()
        }
    }

    fun resetoptions(){
        if(radioButton81.isChecked){
            radioButton81.isChecked=false
        }
        else if (radioButton82.isChecked){
            radioButton82.isChecked=false
        }
        else if (radioButton83.isChecked){
            radioButton83.isChecked=false
        }
        else {
            radioButton84.isChecked=false
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
            submitButton8.visibility=View.VISIBLE
            next8.visibility=View.INVISIBLE
            radioButton81.isEnabled=false
            radioButton82.isEnabled=false
            radioButton83.isEnabled=false
            radioButton84.isEnabled=false
            textView3.visibility=View.INVISIBLE
            Toast.makeText(this,"Press Submit button to view the result",Toast.LENGTH_SHORT).show()
            submitButton8.setOnClickListener {
                resultKaIntent()
            }
        } else
        {
            ref = databaseRef.child("Music")
                .child(qlist.get(iterator))
            Log.d("QuizCategory8","firebase data get" )
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory8","on data change for started"+dataSnapshot.children.toString())
                    val option1Text=dataSnapshot.child("option1").getValue().toString()
                    val option2Text=dataSnapshot.child("option2").getValue().toString()
                    val option3Text=dataSnapshot.child("option3").getValue().toString()
                    val option4Text=dataSnapshot.child("option4").getValue().toString()
                    val questionText=dataSnapshot.child("question").getValue().toString()
                    val quesNo=total.toString()
                    textView5.setText(quesNo+". "+questionText)
                    radioButton81.setText(option1Text)
                    radioButton82.setText(option2Text)
                    radioButton83.setText(option3Text)
                    radioButton84.setText(option4Text)

                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("QuizCategory8","Something Went Wrong!!")
                }
            })
        }
    }

    fun checkCorrectOrWrongForQues(){
        ref = databaseRef.child("Music").child(qlist.get(iterator))
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory8", "Something went Wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val correctAns = dataSnapshot.child("correct").getValue().toString()
                if (radioButton81.isChecked ) {
                    if (radioButton81.text.toString().equals(correctAns)){
                        correct++
                        score++
                        radioButton81.setBackgroundColor(Color.GREEN)
                        radioButton81.isChecked=false
                    } else {
                        wrong=total-correct
                        radioButton81.setBackgroundColor(Color.RED)
                        radioButton81.isChecked=false
                        Toast.makeText(this@QuizCategory8,"Correct Ans:"+correctAns,Toast.LENGTH_SHORT).show()

                    }
                }

                if (radioButton82.isChecked) {
                    if (radioButton82.text.toString().equals(correctAns)) {
                        correct++
                        radioButton82.setBackgroundColor(Color.GREEN)
                        score++
                        radioButton82.isChecked=false
                    } else {
                        wrong=total-correct
                        radioButton82.setBackgroundColor(Color.RED)
                        radioButton83.isChecked=false
                        Toast.makeText(this@QuizCategory8,"Correct Ans:"+correctAns,Toast.LENGTH_SHORT).show()

                    }
                }

                if (radioButton83.isChecked ) {
                    if (radioButton83.text.toString().equals(correctAns)) {
                        correct++
                        radioButton83.setBackgroundColor(Color.GREEN)
                        score++
                        radioButton83.isChecked=false
                    }
                    else {
                        wrong=total-correct
                        radioButton83.setBackgroundColor(Color.RED)
                        radioButton83.isChecked=false
                        Toast.makeText(this@QuizCategory8,"Correct Ans:"+correctAns,Toast.LENGTH_SHORT).show()

                    }
                }

                if (radioButton84.isChecked ) {
                    if (radioButton84.text.toString().equals(correctAns)) {
                        correct++
                        radioButton84.setBackgroundColor(Color.GREEN)
                        score++
                        radioButton84.isChecked=false
                    } else {
                        wrong=total-correct
                        radioButton84.setBackgroundColor(Color.RED)
                        radioButton84.isChecked=false
                        Toast.makeText(this@QuizCategory8,"Correct Ans:"+correctAns,Toast.LENGTH_SHORT).show()

                    }
                }
            }
        })
    }
}