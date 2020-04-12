package com.example.quizofy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_quiz_category1.*

class QuizCategory1 : AppCompatActivity() {
    var correct=0
    var wrong=0
    var score=0
    var total=0
    lateinit var ref:DatabaseReference
    lateinit var timeCount :CountDownTimer
    lateinit var quesList: MutableList<Questions>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_category1)


        radioButton11.isEnabled=false
        radioButton12.isEnabled=false
        radioButton13.isEnabled=false
        radioButton14.isEnabled=false

        startTime.setOnClickListener {
                getQuestion()
                total++
                radioButton11.isEnabled=true
                radioButton12.isEnabled=true
                radioButton13.isEnabled=true
                radioButton14.isEnabled=true
                startTime.visibility=View.INVISIBLE
                startClicked1()
//                checkCorrectorWrong()
        }


        submitButton1.setOnClickListener {
            resultKaIntent1()
            Toast.makeText(this,"Your Result",Toast.LENGTH_SHORT).show()
        }
    }

    fun startClicked1() {

        timeCount =  object : CountDownTimer(10000, 1000) {
            override fun onTick(milisUntilFinished: Long) {
                textView3.text = ("00:" + (1+(milisUntilFinished / 1000)))

                if (radioButton11.isChecked||radioButton12.isChecked||radioButton13.isChecked||radioButton14.isChecked) {
                    optionupdate()
                }
                else {
                    next1.setOnClickListener {
//                      UpdateQuestion()
                        next1.visibility=View.INVISIBLE
                        resetTimer()
                        optionupdate()
                        next1.visibility=View.VISIBLE
                    }
                }
            }
            override fun onFinish() {
                textView3.text = "Time Over!!"
                radioButton11.isEnabled=false
                radioButton12.isEnabled=false
                radioButton13.isEnabled=false
                radioButton14.isEnabled=false
                next1.setOnClickListener {
                    Toast.makeText(this@QuizCategory1,"Press Next again to reset the timer",Toast.LENGTH_SHORT).show()
                    radioButton11.isEnabled=true
                    radioButton12.isEnabled=true
                    radioButton13.isEnabled=true
                    radioButton14.isEnabled=true
                    optionupdate()
                }
            }
        }.start()
    }

    fun resetTimer(){
        timeCount.start()
    }

    fun optionupdate() {
        if (radioButton11.isChecked || radioButton12.isChecked || radioButton13.isChecked || radioButton14.isChecked) {
            radioButton11.isEnabled = false
            radioButton12.isEnabled = false
            radioButton13.isEnabled = false
            radioButton14.isEnabled = false
           next1.setOnClickListener {
               resetTimer()
               resetoptions()
               radioButton11.isEnabled = true
               radioButton12.isEnabled = true
               radioButton13.isEnabled = true
               radioButton14.isEnabled = true
           }

        } else {
            next1.setOnClickListener {
                resetTimer()
            }
        }
    }

    fun resetoptions(){
        if(radioButton11.isChecked){
            radioButton11.isChecked=false
            radioButton11.isEnabled=false
            radioButton12.isEnabled=false
            radioButton13.isEnabled=false
            radioButton14.isEnabled=false
        }
        else if (radioButton12.isChecked){
            radioButton12.isChecked=false
            radioButton11.isEnabled=false
            radioButton12.isEnabled=false
            radioButton13.isEnabled=false
            radioButton14.isEnabled=false

        }
        else if (radioButton13.isChecked){
            radioButton13.isChecked=false
            radioButton11.isEnabled=false
            radioButton12.isEnabled=false
            radioButton13.isEnabled=false
            radioButton14.isEnabled=false
        }
        else {
            radioButton14.isChecked=false
            radioButton11.isEnabled=false
            radioButton12.isEnabled=false
            radioButton13.isEnabled=false
            radioButton14.isEnabled=false
        }
    }

    fun resultKaIntent1(){
        val intent =Intent(this,ResultActivity::class.java)
        startActivity(intent)
        finish()
    }


    fun getQuestion(){

            if (total > 5) {
                val intent = Intent(this, ResultActivity::class.java)
                startActivity(intent)
            } else
    {
        quesList = mutableListOf()
        ref = FirebaseDatabase.getInstance().reference.child("CATEGORIES")
            .child("ART AND LITERATURE").child("Ques1")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (ques in dataSnapshot.children) {
                        val quescontain = ques.getValue(Questions::class.java)
                        quesList.add(quescontain!!)
                        textView5.text = ques.value.toString()
                        radioButton11.text = ques.child("option1").value.toString()
                        radioButton12.text = ques.child("option2").value.toString()
                        radioButton13.text = ques.child("option3").value.toString()
                        radioButton14.text = ques.child("option4").value.toString()
                    }
                }
            }
            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
        }

    fun checkCorrectOrWrong(){

        radioButton11.setOnClickListener {
            if(radioButton11.text.toString().equals(correct)){
                correct++
                radioButton11.setBackgroundColor(Color.GREEN)
                score++
            }
            else{
                wrong++
                radioButton11.setBackgroundColor(Color.RED)
            }
        }

        radioButton12.setOnClickListener {
            if(radioButton12.text.toString().equals(correct)){
                correct++
                radioButton12.setBackgroundColor(Color.GREEN)
                score++
            }
            else{
                wrong++
                radioButton12.setBackgroundColor(Color.RED)
            }
        }

        radioButton13.setOnClickListener {
            if(radioButton13.text.toString().equals(correct)){
                correct++
                radioButton13.setBackgroundColor(Color.GREEN)
                score++
            }
            else{
                wrong++
                radioButton13.setBackgroundColor(Color.RED)
            }
        }

        radioButton14.setOnClickListener {
            if(radioButton14.text.toString().equals(correct)){
                correct++
                radioButton14.setBackgroundColor(Color.GREEN)
                score++
            }
            else{
                wrong++
                radioButton14.setBackgroundColor(Color.RED)
            }
        }
    }
}


