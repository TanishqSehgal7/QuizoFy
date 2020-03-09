package com.example.quizofy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main2_intro_screen.*
import kotlinx.android.synthetic.main.splashscreen.*

class splashActivity : AppCompatActivity() {
    private val DelaySplash: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)
        runAnimationOnTextOfSplashScreen()
        runRotationAnimation()

        Handler().postDelayed(
            {
                val intent=Intent(this,Main2ActivityIntroScreen::class.java)
                startActivity(intent)
                finish()

            }
        ,DelaySplash)
    }

//    fun runAnimationOnIconOfSplashScreen(){
//        val animation=AnimationUtils.loadAnimation(this,R.anim.slide_down)
//        imageView.startAnimation(animation)
//
//    }

    fun runAnimationOnTextOfSplashScreen(){
        val animation2=AnimationUtils.loadAnimation(this,R.anim.fadein)
        tvsplash.startAnimation(animation2)
    }

    fun runRotationAnimation(){
        val animation3=AnimationUtils.loadAnimation(this,R.anim.rorate)
        imageView.startAnimation(animation3)
    }


}