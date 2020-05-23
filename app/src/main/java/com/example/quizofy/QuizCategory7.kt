package com.example.quizofy

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_quiz_category1.*
import kotlinx.android.synthetic.main.activity_quiz_category1.textView3
import kotlinx.android.synthetic.main.activity_quiz_category10.*
import kotlinx.android.synthetic.main.activity_quiz_category4.*
import kotlinx.android.synthetic.main.activity_quiz_category6.*
import kotlinx.android.synthetic.main.activity_quiz_category7.*
import kotlinx.android.synthetic.main.activity_quiz_category7.textView5
import kotlinx.android.synthetic.main.activity_quiz_category9.*
import kotlinx.android.synthetic.main.activity_result.*

class QuizCategory7 : AppCompatActivity() {
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
        setContentView(R.layout.activity_quiz_category7)
        textView5.visibility=View.INVISIBLE
        val ab: ActionBar?=supportActionBar
        if (ab!=null){
            ab.setBackgroundDrawable(getDrawable(R.drawable.actionbargrag))
        }
        Log.d("QuizCategory7","Quiz 7 oncreate called")
        qlist.add("Ques1")
        qlist.add("Ques2")
        qlist.add("Ques3")
        qlist.add("Ques4")
        qlist.add("Ques5")
        Toast.makeText(this,"Press Start Button",Toast.LENGTH_SHORT).show()
        radioButton71.isEnabled=false
        radioButton72.isEnabled=false
        radioButton73.isEnabled=false
        radioButton74.isEnabled=false
        next7.visibility= View.INVISIBLE

        databaseRef=FirebaseDatabase.getInstance().getReference("CATEGORIES")
        submitButton7.visibility=View.INVISIBLE

        startTime7.setOnClickListener {
            startClicked()
            textView3.setTextColor(Color.GREEN)
            getQuestion()
            Log.d("QuizCategory7","get question called and timer started")
            radioButton71.isEnabled=true
            radioButton72.isEnabled=true
            radioButton73.isEnabled=true
            radioButton74.isEnabled=true
            next7.visibility=View.VISIBLE
            startTime7.visibility=View.INVISIBLE
            total++
        }

        next7.setOnClickListener {
            if (iterator<5) {
                radioButton71.isEnabled = true
                radioButton72.isEnabled = true
                radioButton73.isEnabled = true
                radioButton74.isEnabled = true
                radioButton71.setBackgroundColor(Color.TRANSPARENT)
                radioButton72.setBackgroundColor(Color.TRANSPARENT)
                radioButton73.setBackgroundColor(Color.TRANSPARENT)
                radioButton74.setBackgroundColor(Color.TRANSPARENT)
                textView3.setTextColor(Color.GREEN)
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

        submitButton7.setOnClickListener {
            resultKaIntent()
            Toast.makeText(this, "Your Result", Toast.LENGTH_SHORT).show()
        }
    }


    fun startClicked() {
        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("Time Left:"+"00:" + (1 + (milisUntilFinished / 1000)))
                if ((1 + (milisUntilFinished / 1000))<=3){
                    textView3.setTextColor(Color.RED)
                }
                if (radioButton71.isChecked || radioButton72.isChecked || radioButton73.isChecked || radioButton74.isChecked){
                    radioButton71.isEnabled=false
                    radioButton72.isEnabled=false
                    radioButton73.isEnabled=false
                    radioButton74.isEnabled=false
                    checkCorrectOrWrongForQues()
                }

            }
            override fun onFinish() {
                textView3.text = "Time Over!!"
                radioButton71.isEnabled = false
                radioButton72.isEnabled = false
                radioButton73.isEnabled = false
                radioButton74.isEnabled = false
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
    fun resultKaIntent(){
        val intent =Intent(this,ResultActivity::class.java)
        intent.putExtra("correct",correct.toString())
        intent.putExtra("Wrong",wrong.toString())
        intent.putExtra("score",score.toString())
        startActivity(intent)
    }


    fun getQuestion(){
        if (total >=5) {
            submitButton7.visibility=View.VISIBLE
            val anim2=AnimationUtils.loadAnimation(this@QuizCategory7,R.anim.fadein)
            submitButton7.startAnimation(anim2)
            next7.visibility=View.INVISIBLE
            radioButton71.isEnabled=false
            radioButton72.isEnabled=false
            radioButton73.isEnabled=false
            radioButton74.isEnabled=false
            textView3.visibility=View.INVISIBLE
            Toast.makeText(this,"Press Submit button to view the result",Toast.LENGTH_SHORT).show()
            submitButton7.setOnClickListener {
                resultKaIntent()
                finish()
            }
        } else
        {
            ref = databaseRef.child("Entertainment")
                .child(qlist.get(iterator))
            Log.d("QuizCategory7","firebase data get" )
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("QuizCategory7","on data change for started"+dataSnapshot.children.toString())
                    val option1Text=dataSnapshot.child("option1").getValue().toString()
                    val option2Text=dataSnapshot.child("option2").getValue().toString()
                    val option3Text=dataSnapshot.child("option3").getValue().toString()
                    val option4Text=dataSnapshot.child("option4").getValue().toString()
                    val questionText=dataSnapshot.child("question").getValue().toString()
                    val quesNo=total.toString()
                    val anim= AnimationUtils.loadAnimation(this@QuizCategory7,R.anim.slide_down)
                    textView5.startAnimation(anim)
                    textView5.visibility=View.VISIBLE
                    textView5.setText(quesNo+". "+questionText)
                    radioButton71.startAnimation(anim)
                    radioButton71.setText(option1Text)
                    radioButton72.startAnimation(anim)
                    radioButton72.setText(option2Text)
                    radioButton73.startAnimation(anim)
                    radioButton73.setText(option3Text)
                    radioButton74.startAnimation(anim)
                    radioButton74.setText(option4Text)

                }
                override fun onCancelled(p0: DatabaseError) {
                    Log.e("QuizCategory7","Something Went Wrong!!")
                }
            })
        }
    }

    fun checkCorrectOrWrongForQues(){
        ref = databaseRef.child("Entertainment").child(qlist.get(iterator))
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("QuizCategory1", "Something went Wrong!!")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val correctAns = dataSnapshot.child("correct").getValue().toString()
                if (radioButton71.isChecked ) {
                    if (radioButton71.text.toString().equals(correctAns)){
                        correct++
                        score++
                        radioButton71.setBackgroundColor(Color.GREEN)
                        radioButton71.isChecked=false
                    } else {
                        wrong=total-correct
                        radioButton71.setBackgroundColor(Color.RED)
                        radioButton71.isChecked=false
                        Toast.makeText(this@QuizCategory7,"Correct Ans:"+correctAns,Toast.LENGTH_SHORT).show()
                    }
                }

                if (radioButton72.isChecked) {
                    if (radioButton72.text.toString().equals(correctAns)) {
                        correct++
                        radioButton72.setBackgroundColor(Color.GREEN)
                        score++
                        radioButton72.isChecked=false
                    } else {
                        wrong=total-correct
                        radioButton72.setBackgroundColor(Color.RED)
                        radioButton73.isChecked=false
                        Toast.makeText(this@QuizCategory7,"Correct Ans:"+correctAns,Toast.LENGTH_SHORT).show()

                    }
                }

                if (radioButton73.isChecked ) {
                    if (radioButton73.text.toString().equals(correctAns)) {
                        correct++
                        radioButton73.setBackgroundColor(Color.GREEN)
                        score++
                        radioButton73.isChecked=false
                    }
                    else {
                        wrong=total-correct
                        radioButton73.setBackgroundColor(Color.RED)
                        radioButton73.isChecked=false
                        Toast.makeText(this@QuizCategory7,"Correct Ans:"+correctAns,Toast.LENGTH_SHORT).show()

                    }
                }

                if (radioButton74.isChecked ) {
                    if (radioButton74.text.toString().equals(correctAns)) {
                        correct++
                        radioButton74.setBackgroundColor(Color.GREEN)
                        score++
                        radioButton74.isChecked=false
                    } else {
                        wrong=total-correct
                        radioButton74.setBackgroundColor(Color.RED)
                        radioButton74.isChecked=false
                        Toast.makeText(this@QuizCategory7,"Correct Ans:"+correctAns,Toast.LENGTH_SHORT).show()

                    }
                }
            }
        })
    }
}