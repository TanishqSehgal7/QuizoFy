package com.example.quizofy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_MESSAGE = "com.example.quizofy.MESSEGE"


class MainActivity : AppCompatActivity(){

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    c1.isClickable=true
                    c2.isClickable=true
                    c3.isClickable=true
                    c4.isClickable=true
                    c5.isClickable=true
                    c6.isClickable=true
                    c7.isClickable=true
                    c8.isClickable=true
                    c9.isClickable=true
                    c10.isClickable=true
                    startActivity(intent)
                    finish()
//                    switchbwtweenfragments(NotificationsFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.notify -> {
                    c1.isClickable=false
                    c2.isClickable=false
                    c3.isClickable=false
                    c4.isClickable=false
                    c5.isClickable=false
                    c6.isClickable=false
                    c7.isClickable=false
                    c8.isClickable=false
                    c9.isClickable=false
                    c10.isClickable=false
                    switchbwtweenfragments(NotificationsFragment())

                    return@OnNavigationItemSelectedListener true
                }
                R.id.Theme -> {
                    c1.isClickable=false
                    c2.isClickable=false
                    c3.isClickable=false
                    c4.isClickable=false
                    c5.isClickable=false
                    c6.isClickable=false
                    c7.isClickable=false
                    c8.isClickable=false
                    c9.isClickable=false
                    c10.isClickable=false
                    switchbwtweenfragments(ThemeFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.aboutApp -> {
                    c1.isClickable=false
                    c2.isClickable=false
                    c3.isClickable=false
                    c4.isClickable=false
                    c5.isClickable=false
                    c6.isClickable=false
                    c7.isClickable=false
                    c8.isClickable=false
                    c9.isClickable=false
                    c10.isClickable=false
                    switchbwtweenfragments(AboutAppFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false

        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        runAnimationOnCategoryTv()
        Log.d("QuizCategory1","Main activity oncreate called")
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        FirebaseMessaging.getInstance().isAutoInitEnabled=true


    }
   fun startQuiz1(view: View){
           val button = findViewById<Button>(R.id.c1)
           val intent = Intent(this, QuizCategory1::class.java)
           Log.d("QuizCategory1", "Quiz 1 started")
           startActivity(intent)
           finish()
   }

    fun startQuiz2(view: View){
            val button = findViewById<Button>(R.id.c2)
            val intent = Intent(this, QuizCategory2::class.java)
            startActivity(intent)
            finish()
    }
    fun startQuiz3(view: View){
            val button = findViewById<Button>(R.id.c3)
            val intent = Intent(this, QuizCategory3::class.java)
            startActivity(intent)
            finish()
    }

    fun startQuiz4(view: View){
            val button = findViewById<Button>(R.id.c4)
            val intent = Intent(this, QuizCategory4::class.java)
            startActivity(intent)
            finish()
    }

    fun startQuiz5(view: View){
            val button = findViewById<Button>(R.id.c5)
            val intent = Intent(this, QuizCategory5::class.java)
            startActivity(intent)
            finish()
    }

    fun startQuiz6(view: View){
            val button = findViewById<Button>(R.id.c6)
            val intent = Intent(this, QuizCategory6::class.java)
            startActivity(intent)
            finish()
    }

    fun startQuiz7(view: View){
            val button = findViewById<Button>(R.id.c7)
            val intent = Intent(this, QuizCategory7::class.java)
            startActivity(intent)
            finish()
    }
    fun startQuiz8(view: View){
        val button=findViewById<Button>(R.id.c8)
        val intent=Intent(this,QuizCategory8::class.java)
        startActivity(intent)
        finish()
    }

    fun startQuiz9(view: View){
            val button = findViewById<Button>(R.id.c9)
            val intent = Intent(this, QuizCategory9::class.java)
            startActivity(intent)
            finish()
    }

    fun startQuiz10(view: View){
            val button = findViewById<Button>(R.id.c10)
            val intent = Intent(this, QuizCategory10::class.java)
            startActivity(intent)
            finish()
    }

    fun switchbwtweenfragments(fragment: Fragment) {

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containFragment, fragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.isAddToBackStackAllowed
        Log.d("QuizCategory1","frag is loaded")
        fragmentTransaction.commit()

    }

    fun runAnimationOnCategoryTv(){
        val animCategory=AnimationUtils.loadAnimation(this,R.anim.fadein)
        //categorytv.startAnimation(animCategory)
        c1.startAnimation(animCategory)
        c2.startAnimation(animCategory)
        c3.startAnimation(animCategory)
        c4.startAnimation(animCategory)
        c5.startAnimation(animCategory)
        c6.startAnimation(animCategory)
        c7.startAnimation(animCategory)
        c8.startAnimation(animCategory)
        c9.startAnimation(animCategory)
        c10.startAnimation(animCategory)
        bottomNavigation.startAnimation(animCategory)

    }
}
