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
import kotlinx.android.synthetic.main.activity_quiz_category3.textView5
import kotlinx.android.synthetic.main.activity_quiz_category6.*
import kotlinx.android.synthetic.main.activity_quiz_category9.*
import kotlinx.android.synthetic.main.activity_result.*

class QuizCategory3 : AppCompatActivity() {
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
        setContentView(R.layout.activity_quiz_category3)
        Log.d("QuizCategory3","Quiz 3 oncreate called")
        qlist.add("Ques1")
        qlist.add("Ques2")
        qlist.add("Ques3")
        qlist.add("Ques4")
        qlist.add("Ques5")
        Toast.makeText(this,"Press Start Button",Toast.LENGTH_SHORT).show()
        radioButton31.isEnabled=false
        radioButton32.isEnabled=false
        radioButton33.isEnabled=false
        radioButton34.isEnabled=false
        next3.visibility=View.INVISIBLE

        databaseRef=FirebaseDatabase.getInstance().getReference("CATEGORIES")
        submitButton3.visibility=View.INVISIBLE

        startTime3.setOnClickListener {
            startClicked2()
            getQuestion()
            Log.d("QuizCategory3","get question called and timer started")
            radioButton31.isEnabled=true
            radioButton32.isEnabled=true
            radioButton33.isEnabled=true
            radioButton34.isEnabled=true
            next3.visibility=View.VISIBLE
            startTime3.visibility=View.INVISIBLE
            total++
        }

        next3.setOnClickListener {
            if (iterator<5) {
                radioButton31.isEnabled = true
                radioButton32.isEnabled = true
                radioButton33.isEnabled = true
                radioButton34.isEnabled = true
                radioButton31.setBackgroundColor(Color.TRANSPARENT)
                radioButton32.setBackgroundColor(Color.TRANSPARENT)
                radioButton33.setBackgroundColor(Color.TRANSPARENT)
                radioButton34.setBackgroundColor(Color.TRANSPARENT)
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

        submitButton3.setOnClickListener {
            resultKaIntent()
            Toast.makeText(this, "Your Result", Toast.LENGTH_SHORT).show()
        }
    }


    fun startClicked2() {
        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("Time Left:"+"00:" + (1 + (milisUntilFinished / 1000)))
                if (radioButton31.isChecked || radioButton32.isChecked || radioButton33.isChecked || radioButton34.isChecked){
                    radioButton31.isEnabled=false
                    radioButton32.isEnabled=false
                    radioButton33.isEnabled=false
                    radioButton34.isEnabled=false
                    checkCorrectOrWrongForQues()
                }

            }
            override fun onFinish() {
                textView3.text = "Time Over!!"
                radioButton31.isEnabled = false
                radioButton32.isEnabled = false
                radioButton33.isEnabled = false
                radioButton34.isEnabled = false
            }
        }.start()

    }

    fun resetTimer(){
        timeCount.start()
    }

    fun optionupdate() {
        if (radioButton31.isChecked || radioButton32.isChecked || radioButton33.isChecked || radioButton34.isChecked) {
            resetoptions()
        } else {
            resetTimer()
        }
    }

    fun resetoptions(){
        if(radioButton31.isChecked){
            radioButton31.isChecked=false
        }
        else if (radioButton32.isChecked){
            radioButton32.isChecked=false
        }
        else if (radioButton33.isChecked){
            radioButton33.isChecked=false
        }
        else {
            radioButton34.isChecked=false
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
            submitButton3.visibility=View.VISIBLE
            next3.visibility=View.INVISIBLE
            radioButton31.isEnabled=false
            radioButton32.isEnabled=false
            radioButton33.isEnabled=false
            radioButton34.isEnabled=false
            textView3.visibility=View.INVISIBLE
            Toast.makeText(this,"Press Submit button to view the result",Toast.LENGTH_SHORT).show()
            submitButton3.setOnClickListener {
                resultKaIntent()
            }
        } else
        {
            ref = databaseRef.child("General Knowledge")
                .child(qlist.get(iterator))
            Log.d("QuizCategory3","firebase data get" )
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory3","on data change for started"+dataSnapshot.children.toString())
                    val option1Text=dataSnapshot.child("option1").getValue().toString()
                    val option2Text=dataSnapshot.child("option2").getValue().toString()
                    val option3Text=dataSnapshot.child("option3").getValue().toString()
                    val option4Text=dataSnapshot.child("option4").getValue().toString()
                    val questionText=dataSnapshot.child("question").getValue().toString()
                    val quesNo=total.toString()
                    textView5.setText(quesNo+". "+questionText)
                    radioButton31.setText(option1Text)
                    radioButton32.setText(option2Text)
                    radioButton33.setText(option3Text)
                    radioButton34.setText(option4Text)

                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("QuizCategory3","Something Went Wrong!!")
                }
            })
        }
    }


    fun checkCorrectOrWrongForQues(){
        ref = databaseRef.child("General Knowledge").child(qlist.get(iterator))
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory3", "Something went Wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val correctAns = dataSnapshot.child("correct").getValue().toString()
                if (radioButton31.isChecked ) {
                    if (radioButton31.text.toString().equals(correctAns)){
                        correct++
                        score++
                        radioButton31.setBackgroundColor(Color.GREEN)
                        radioButton31.isChecked=false
                    } else {
                        wrong=total-correct
                        radioButton31.setBackgroundColor(Color.RED)
                        radioButton31.isChecked=false
                        Toast.makeText(this@QuizCategory3,"Correct Ans:"+correctAns,Toast.LENGTH_SHORT).show()
                    }
                }

                if (radioButton32.isChecked) {
                    if (radioButton32.text.toString().equals(correctAns)) {
                        correct++
                        radioButton32.setBackgroundColor(Color.GREEN)
                        score++
                        radioButton32.isChecked=false
                    } else {
                        wrong=total-correct
                        radioButton32.setBackgroundColor(Color.RED)
                        radioButton33.isChecked=false
                        Toast.makeText(this@QuizCategory3,"Correct Ans:"+correctAns,Toast.LENGTH_SHORT).show()
                    }
                }

                if (radioButton33.isChecked ) {
                    if (radioButton33.text.toString().equals(correctAns)) {
                        correct++
                        radioButton33.setBackgroundColor(Color.GREEN)
                        score++
                        radioButton33.isChecked=false
                    }
                    else {
                        wrong=total-correct
                        radioButton33.setBackgroundColor(Color.RED)
                        radioButton33.isChecked=false
                        Toast.makeText(this@QuizCategory3,"Correct Ans:"+correctAns,Toast.LENGTH_SHORT).show()
                    }
                }

                if (radioButton34.isChecked ) {
                    if (radioButton34.text.toString().equals(correctAns)) {
                        correct++
                        radioButton34.setBackgroundColor(Color.GREEN)
                        score++
                        radioButton34.isChecked=false
                    } else {
                        wrong=total-correct
                        radioButton34.setBackgroundColor(Color.RED)
                        radioButton34.isChecked=false
                        Toast.makeText(this@QuizCategory3,"Correct Ans:"+correctAns,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}

