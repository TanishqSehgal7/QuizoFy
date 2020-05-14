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
    lateinit var ref:DatabaseReference
    private lateinit var databaseRef:DatabaseReference
    lateinit var timeCount :CountDownTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category9)
        Log.d("QuizCategory9","Quiz 9 oncreate called")
        qlist.add("Ques1")
        qlist.add("Ques2")
        qlist.add("Ques3")
        qlist.add("Ques4")
        qlist.add("Ques5")
        Toast.makeText(this,"Press Start Button",Toast.LENGTH_SHORT).show()
        radioButton91.isEnabled=false
        radioButton92.isEnabled=false
        radioButton93.isEnabled=false
        radioButton94.isEnabled=false
        next9.visibility=View.INVISIBLE

        databaseRef=FirebaseDatabase.getInstance().getReference("CATEGORIES")
        submitButton9.visibility=View.INVISIBLE

        startTime9.setOnClickListener {
            startClicked()
            getQuestion()
            Log.d("QuizCategory9","get question called and timer started")
            radioButton91.isEnabled=true
            radioButton92.isEnabled=true
            radioButton93.isEnabled=true
            radioButton94.isEnabled=true
            next9.visibility=View.VISIBLE
            startTime9.visibility=View.INVISIBLE
            total++
        }

        next9.setOnClickListener {
            if (iterator<5) {
                radioButton91.isEnabled = true
                radioButton92.isEnabled = true
                radioButton93.isEnabled = true
                radioButton94.isEnabled = true
                radioButton91.setBackgroundColor(Color.TRANSPARENT)
                radioButton92.setBackgroundColor(Color.TRANSPARENT)
                radioButton93.setBackgroundColor(Color.TRANSPARENT)
                radioButton94.setBackgroundColor(Color.TRANSPARENT)
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

        submitButton9.setOnClickListener {
            resultKaIntent()
            Toast.makeText(this, "Your Result", Toast.LENGTH_SHORT).show()
        }
    }


    fun startClicked() {
        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("Time Left:"+"00:" + (1 + (milisUntilFinished / 1000)))
                if (radioButton91.isChecked || radioButton92.isChecked || radioButton93.isChecked || radioButton94.isChecked){
                    radioButton91.isEnabled=false
                    radioButton92.isEnabled=false
                    radioButton93.isEnabled=false
                    radioButton94.isEnabled=false
                    checkCorrectOrWrongForQues()
                }

            }
            override fun onFinish() {
                textView3.text = "Time Over!!"
                radioButton91.isEnabled = false
                radioButton92.isEnabled = false
                radioButton93.isEnabled = false
                radioButton94.isEnabled = false
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
    fun resultKaIntent(){
        val intent =Intent(this,ResultActivity::class.java)
        intent.putExtra("correct",correct.toString())
        intent.putExtra("Wrong",wrong.toString())
        intent.putExtra("score",score.toString())
        startActivity(intent)
    }


    fun getQuestion(){
        if (total >=5) {
            submitButton9.visibility=View.VISIBLE
            next9.visibility=View.INVISIBLE
            radioButton91.isEnabled=false
            radioButton92.isEnabled=false
            radioButton93.isEnabled=false
            radioButton94.isEnabled=false
            textView3.visibility=View.INVISIBLE
            Toast.makeText(this,"Press Submit button to view the result",Toast.LENGTH_SHORT).show()
            submitButton9.setOnClickListener {
                resultKaIntent()
            }
        } else
        {
            ref = databaseRef.child("Sneaker Culture")
                .child(qlist.get(iterator))
            Log.d("QuizCategory9","firebase data get" )
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory9","on data change for started"+dataSnapshot.children.toString())
                    val option1Text=dataSnapshot.child("option1").getValue().toString()
                    val option2Text=dataSnapshot.child("option2").getValue().toString()
                    val option3Text=dataSnapshot.child("option3").getValue().toString()
                    val option4Text=dataSnapshot.child("option4").getValue().toString()
                    val questionText=dataSnapshot.child("question").getValue().toString()
                    val quesNo=total.toString()
                    textView5.setText(quesNo+". "+questionText)
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

    fun checkCorrectOrWrongForQues(){
        ref = databaseRef.child("Sneaker Culture").child(qlist.get(iterator))
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory9", "Something went Wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val correctAns = dataSnapshot.child("correct").getValue().toString()
                if (radioButton91.isChecked ) {
                    if (radioButton91.text.toString().equals(correctAns)){
                        correct++
                        score++
                        radioButton91.setBackgroundColor(Color.GREEN)
                        radioButton91.isChecked=false
                    } else {
                        wrong=total-correct
                        radioButton91.setBackgroundColor(Color.RED)
                        radioButton91.isChecked=false
                    }
                }

                if (radioButton92.isChecked) {
                    if (radioButton92.text.toString().equals(correctAns)) {
                        correct++
                        radioButton92.setBackgroundColor(Color.GREEN)
                        score++
                        radioButton92.isChecked=false
                    } else {
                        wrong=total-correct
                        radioButton92.setBackgroundColor(Color.RED)
                        radioButton93.isChecked=false
                    }
                }

                if (radioButton93.isChecked ) {
                    if (radioButton93.text.toString().equals(correctAns)) {
                        correct++
                        radioButton93.setBackgroundColor(Color.GREEN)
                        score++
                        radioButton93.isChecked=false
                    }
                    else {
                        wrong=total-correct
                        radioButton93.setBackgroundColor(Color.RED)
                        radioButton93.isChecked=false
                    }
                }

                if (radioButton94.isChecked ) {
                    if (radioButton94.text.toString().equals(correctAns)) {
                        correct++
                        radioButton94.setBackgroundColor(Color.GREEN)
                        score++
                        radioButton94.isChecked=false
                    } else {
                        wrong=total-correct
                        radioButton94.setBackgroundColor(Color.RED)
                        radioButton94.isChecked=false
                    }
                }
            }
        })
    }
}

