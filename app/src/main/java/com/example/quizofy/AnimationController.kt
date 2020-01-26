package com.example.quizofy

import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main2_intro_screen.*

class AnimationController:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2_intro_screen)

    }

//    fun CategorybuttonClickEffect(button:View){

//       intro.setOnTouchListener{ v,event->
//            when(event.action){
//               MotionEvent.ACTION_BUTTON_PRESS->{
//                   v.background.colorFilter()
//                }
//            }
//        }

//        intro.setOnTouchListener { v, event ->
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    v.background.setColorFilter(-0x1f0b8adf, PorterDuff.Mode.SRC_ATOP)
//                    v.invalidate()
//                }
//                MotionEvent.ACTION_UP -> {
//                    v.background.clearColorFilter()
//                    v.invalidate()
//                }
//            }
//            false
//        }
//    }
}