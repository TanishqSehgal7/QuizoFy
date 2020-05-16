package com.example.quizofy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2_intro_screen.*
import kotlinx.android.synthetic.main.activity_quiz_category1.*
import kotlinx.android.synthetic.main.splashscreen.*

class Main2ActivityIntroScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2_intro_screen)

        val ab:ActionBar?=supportActionBar
        if (ab!=null){
            ab.setBackgroundDrawable(getDrawable(R.drawable.actionbargrag))
        }

        Toast.makeText(this,"Tap the button to browse categories",Toast.LENGTH_SHORT).show()

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                Log.d("QuizCategory1", "Token:"+ token)
            })
        runAnimationFadeout()
        runAnimationZoom()
            intro.setOnClickListener {
                startIntent()
            }

    }

    fun startIntent(){
        val intent=Intent(this,MainActivity::class.java)
        Log.d("QuizCategory1","Main activity call")
        startActivity(intent)
    }

    fun runAnimationZoom(){
        val animation1=AnimationUtils.loadAnimation(this,R.anim.zoomin)
        textView4.startAnimation(animation1)
    }

    fun runAnimationFadeout(){
        val animation2=AnimationUtils.loadAnimation(this,R.anim.fadein)
        introLayout.startAnimation(animation2)
    }

}