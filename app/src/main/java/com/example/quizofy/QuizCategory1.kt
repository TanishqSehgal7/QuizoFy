package com.example.quizofy

import android.content.Intent
import android.content.IntentSender
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.renderscript.Sampler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_quiz_category1.*
import kotlinx.android.synthetic.main.activity_quiz_category1.textView3
import kotlinx.android.synthetic.main.activity_quiz_category1.textView5
import kotlinx.android.synthetic.main.activity_quiz_category10.*
import kotlinx.android.synthetic.main.activity_quiz_category9.*
import kotlinx.android.synthetic.main.activity_result.*
import java.util.*
import kotlin.collections.ArrayList

class QuizCategory1 : AppCompatActivity() {
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
        setContentView(R.layout.activity_quiz_category1)
        Log.d("QuizCategory1","Quiz 1 oncreate called")
        qlist.add("Ques1")
        qlist.add("Ques2")
        qlist.add("Ques3")
        qlist.add("Ques4")
        qlist.add("Ques5")
        Toast.makeText(this,"Press Start Button",Toast.LENGTH_SHORT).show()
        radioButton11.isEnabled=false
        radioButton12.isEnabled=false
        radioButton13.isEnabled=false
        radioButton14.isEnabled=false

        databaseRef=FirebaseDatabase.getInstance().getReference("CATEGORIES")
        submitButton1.visibility=View.INVISIBLE
        next1.visibility=View.INVISIBLE

        startTime.setOnClickListener {
                startClicked1()
                getQuestion()
                Log.d("QuizCategory1","get question called and timer started")
                radioButton11.isEnabled=true
                radioButton12.isEnabled=true
                radioButton13.isEnabled=true
                radioButton14.isEnabled=true
                next1.visibility=View.VISIBLE
                startTime.visibility=View.INVISIBLE
                total++
            submitButton1.setOnClickListener {
                if (iterator<5)
                    resultKaIntent()
                Toast.makeText(this,"Your Result",Toast.LENGTH_SHORT).show()
            }

        }

        next1.setOnClickListener {
            if (iterator<5) {
                radioButton11.isEnabled = true
                radioButton12.isEnabled = true
                radioButton13.isEnabled = true
                radioButton14.isEnabled = true
                radioButton11.setBackgroundColor(Color.TRANSPARENT)
                radioButton12.setBackgroundColor(Color.TRANSPARENT)
                radioButton13.setBackgroundColor(Color.TRANSPARENT)
                radioButton14.setBackgroundColor(Color.TRANSPARENT)
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
    }


    fun startClicked1() {
        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("Time Left:"+"00:" + (1 + (milisUntilFinished / 1000)))
                if (radioButton11.isChecked || radioButton12.isChecked || radioButton13.isChecked || radioButton14.isChecked){
                    radioButton11.isEnabled=false
                    radioButton12.isEnabled=false
                    radioButton13.isEnabled=false
                    radioButton14.isEnabled=false
                    checkCorrectOrWrongForQues()
                }

                }
                override fun onFinish() {
                    textView3.text = "Time Over!!"
                    radioButton11.isEnabled = false
                    radioButton12.isEnabled = false
                    radioButton13.isEnabled = false
                    radioButton14.isEnabled = false
                }
            }.start()

    }

    fun resetTimer(){
        timeCount.start()
    }

    fun optionupdate() {
        if (radioButton11.isChecked || radioButton12.isChecked || radioButton13.isChecked || radioButton14.isChecked) {
               resetoptions()
        } else {
                resetTimer()
        }
    }

    fun resetoptions(){
        if(radioButton11.isChecked){
            radioButton11.isChecked=false
        }
        else if (radioButton12.isChecked){
            radioButton12.isChecked=false
        }
        else if (radioButton13.isChecked){
            radioButton13.isChecked=false
        }
        else {
            radioButton14.isChecked=false
        }
    }
    fun resultKaIntent(){
        val intent =Intent(this,ResultActivity::class.java)
        intent.putExtra("correct",correct)
        intent.putExtra("Wrong",wrong)
        intent.putExtra("score",score)
        startActivity(intent)
    }


    fun getQuestion(){
        if (total >= 5) {
            submitButton1.visibility=View.VISIBLE
            radioButton11.isEnabled=false
            radioButton12.isEnabled=false
            radioButton13.isEnabled=false
            radioButton14.isEnabled=false
            textView3.visibility=View.INVISIBLE
            Toast.makeText(this,"Press Submit button to view the result",Toast.LENGTH_SHORT).show()
            submitButton1.setOnClickListener {
                resultKaIntent()
            }
        }else
        {
            ref = databaseRef.child("Art and Literature")
                .child(qlist.get(iterator))
            Log.d("QuizCategory1","firebase data get" )
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory1","on data change for started"+dataSnapshot.children.toString())
                    val option1Text=dataSnapshot.child("option1").getValue().toString()
                    val option2Text=dataSnapshot.child("option2").getValue().toString()
                    val option3Text=dataSnapshot.child("option3").getValue().toString()
                    val option4Text=dataSnapshot.child("option4").getValue().toString()
                    val questionText=dataSnapshot.child("question").getValue().toString()
                    val quesNo=total.toString()
                    textView5.setText(quesNo+". "+questionText)
                    radioButton11.setText(option1Text)
                    radioButton12.setText(option2Text)
                    radioButton13.setText(option3Text)
                    radioButton14.setText(option4Text)
                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("QuizCategory1","Something Went Wrong!!")
                }
            })
        }
    }


    fun checkCorrectOrWrongForQues(){
        ref = databaseRef.child("Art and Literature").child(qlist.get(iterator))
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory1", "Something went Wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val totalScore=score.toString()
                val correctAns = dataSnapshot.child("correct").getValue().toString()
                if (radioButton11.isChecked ) {
                    if (radioButton11.text.toString().equals(correctAns)){
                        correct++
                        score++
                        radioButton11.setBackgroundColor(Color.GREEN)
                        sct1.setText("Score: "+totalScore)
                    } else {
                        wrong++
                        radioButton11.setBackgroundColor(Color.RED)
                    }
                }

                if (radioButton12.isChecked) {
                    if (radioButton12.text.toString().equals(correctAns)) {
                        correct++
                        radioButton12.setBackgroundColor(Color.GREEN)
                        score++
                        sct1.setText("Score: "+totalScore)
                    } else {
                        wrong++
                        radioButton12.setBackgroundColor(Color.RED)
                    }
                }

                if (radioButton13.isChecked ) {
                    if (radioButton13.text.toString().equals(correctAns)) {
                        correct++
                        radioButton13.setBackgroundColor(Color.GREEN)
                        score++
                        sct1.setText("Score: "+totalScore)
                    }
                    else {
                        wrong++
                        radioButton13.setBackgroundColor(Color.RED)
                    }
                }

                if (radioButton14.isChecked ) {
                    if (radioButton14.text.toString().equals(correctAns)) {
                        correct++
                        radioButton14.setBackgroundColor(Color.GREEN)
                        score++
                        sct1.setText("Score: "+totalScore)
                    } else {
                        wrong++
                        radioButton14.setBackgroundColor(Color.RED)
                    }
                }
            }
        })
    }
}


