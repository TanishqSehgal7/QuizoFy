package com.example.quizofy

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main2_intro_screen.*

class AnimationController:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val animation1 = AnimationUtils.loadAnimation(this, R.anim.outfade)
        textView4.startAnimation(animation1)
        Handler().postDelayed({
            textView4.visibility = View.GONE
            val animation2 = AnimationUtils.loadAnimation(this,R.anim.slideup)
            intro.startAnimation(animation2)
        }, 1000)
    }
}