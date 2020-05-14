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
import kotlinx.android.synthetic.main.activity_quiz_category2.*
import kotlinx.android.synthetic.main.activity_quiz_category3.*
import kotlinx.android.synthetic.main.activity_quiz_category4.*
import kotlinx.android.synthetic.main.activity_quiz_category4.textView5
import kotlinx.android.synthetic.main.activity_quiz_category9.*
import kotlinx.android.synthetic.main.activity_result.*

class QuizCategory4 : AppCompatActivity() {
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
        setContentView(R.layout.activity_quiz_category4)
        Log.d("QuizCategory4","Quiz 4 oncreate called")
        qlist.add("Ques1")
        qlist.add("Ques2")
        qlist.add("Ques3")
        qlist.add("Ques4")
        qlist.add("Ques5")
        Toast.makeText(this,"Press Start Button",Toast.LENGTH_SHORT).show()
        radioButton41.isEnabled=false
        radioButton42.isEnabled=false
        radioButton43.isEnabled=false
        radioButton44.isEnabled=false
        next4.visibility=View.INVISIBLE

        databaseRef=FirebaseDatabase.getInstance().getReference("CATEGORIES")
        submitButton4.visibility=View.INVISIBLE

        startTime4.setOnClickListener {
            startClicked()
            getQuestion()
            Log.d("QuizCategory4","get question called and timer started")
            radioButton41.isEnabled=true
            radioButton42.isEnabled=true
            radioButton43.isEnabled=true
            radioButton44.isEnabled=true
            next4.visibility=View.VISIBLE
            startTime4.visibility=View.INVISIBLE
            total++
        }

        next4.setOnClickListener {
            if (iterator<5) {
                radioButton41.isEnabled = true
                radioButton42.isEnabled = true
                radioButton43.isEnabled = true
                radioButton44.isEnabled = true
                radioButton41.setBackgroundColor(Color.TRANSPARENT)
                radioButton42.setBackgroundColor(Color.TRANSPARENT)
                radioButton43.setBackgroundColor(Color.TRANSPARENT)
                radioButton44.setBackgroundColor(Color.TRANSPARENT)
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

        submitButton4.setOnClickListener {
            resultKaIntent()
            Toast.makeText(this, "Your Result", Toast.LENGTH_SHORT).show()
        }
    }


    fun startClicked() {
        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("Time Left:"+"00:" + (1 + (milisUntilFinished / 1000)))
                if (radioButton41.isChecked || radioButton42.isChecked || radioButton43.isChecked || radioButton44.isChecked){
                    radioButton41.isEnabled=false
                    radioButton42.isEnabled=false
                    radioButton43.isEnabled=false
                    radioButton44.isEnabled=false
                    checkCorrectOrWrongForQues()
                }

            }
            override fun onFinish() {
                textView3.text = "Time Over!!"
                radioButton41.isEnabled = false
                radioButton42.isEnabled = false
                radioButton43.isEnabled = false
                radioButton44.isEnabled = false
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
    fun resultKaIntent(){
        val intent =Intent(this,ResultActivity::class.java)
        intent.putExtra("correct",correct.toString())
        intent.putExtra("Wrong",wrong.toString())
        intent.putExtra("score",score.toString())
        startActivity(intent)
    }


    fun getQuestion(){
        if (total >=5) {
            submitButton4.visibility=View.VISIBLE
            next4.visibility=View.INVISIBLE
            radioButton41.isEnabled=false
            radioButton42.isEnabled=false
            radioButton43.isEnabled=false
            radioButton44.isEnabled=false
            textView3.visibility=View.INVISIBLE
            Toast.makeText(this,"Press Submit button to view the result",Toast.LENGTH_SHORT).show()
            submitButton4.setOnClickListener {
                resultKaIntent()
            }
        } else
        {
            ref = databaseRef.child("History")
                .child(qlist.get(iterator))
            Log.d("QuizCategory4","firebase data get" )
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory4","on data change for started"+dataSnapshot.children.toString())
                    val option1Text=dataSnapshot.child("option1").getValue().toString()
                    val option2Text=dataSnapshot.child("option2").getValue().toString()
                    val option3Text=dataSnapshot.child("option3").getValue().toString()
                    val option4Text=dataSnapshot.child("option4").getValue().toString()
                    val questionText=dataSnapshot.child("question").getValue().toString()
                    val quesNo=total.toString()
                    textView5.setText(quesNo+". "+questionText)
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



    fun checkCorrectOrWrongForQues(){
        ref = databaseRef.child("History").child(qlist.get(iterator))
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory4", "Something went Wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val correctAns = dataSnapshot.child("correct").getValue().toString()
                if (radioButton41.isChecked ) {
                    if (radioButton41.text.toString().equals(correctAns)){
                        correct++
                        score++
                        radioButton41.setBackgroundColor(Color.GREEN)
                        radioButton41.isChecked=false
                    } else {
                        wrong=total-correct
                        radioButton41.setBackgroundColor(Color.RED)
                        radioButton41.isChecked=false
                    }
                }

                if (radioButton42.isChecked) {
                    if (radioButton42.text.toString().equals(correctAns)) {
                        correct++
                        radioButton42.setBackgroundColor(Color.GREEN)
                        score++
                        radioButton42.isChecked=false
                    } else {
                        wrong=total-correct
                        radioButton42.setBackgroundColor(Color.RED)
                        radioButton42.isChecked=false
                    }
                }

                if (radioButton43.isChecked ) {
                    if (radioButton43.text.toString().equals(correctAns)) {
                        correct++
                        radioButton43.setBackgroundColor(Color.GREEN)
                        score++
                        radioButton43.isChecked=false
                    }
                    else {
                        wrong=total-correct
                        radioButton43.setBackgroundColor(Color.RED)
                        radioButton43.isChecked=false
                    }
                }

                if (radioButton44.isChecked ) {
                    if (radioButton44.text.toString().equals(correctAns)) {
                        correct++
                        radioButton44.setBackgroundColor(Color.GREEN)
                        score++
                        radioButton44.isChecked=false
                    } else {
                        wrong=total-correct
                        radioButton44.setBackgroundColor(Color.RED)
                        radioButton44.isChecked=false
                    }
                }
            }
        })
    }
}
