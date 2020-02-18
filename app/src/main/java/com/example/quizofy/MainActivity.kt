package com.example.quizofy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_MESSAGE = "com.example.quizofy.MESSEGE"


class MainActivity : AppCompatActivity(){

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    Toast.makeText(this, "Navigating to Home", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.notify -> {
                    Toast.makeText(this, "Showing Notifications", Toast.LENGTH_SHORT).show()
                    switchbwtweenfragments(NotificationsFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.Score -> {
                    Toast.makeText(this, "Your Scores", Toast.LENGTH_SHORT).show()
                    switchbwtweenfragments(ScoreFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.aboutApp -> {
                    Toast.makeText(this, "Developer Details", Toast.LENGTH_SHORT).show()
                    switchbwtweenfragments(AboutAppFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
   fun startQuiz1(view: View){
       val button=findViewById<Button>(R.id.c1)
       val intent=Intent(this,QuizCategory1::class.java)
       startActivity(intent)
       finish()
       Toast.makeText(this,"Art and Literature",Toast.LENGTH_SHORT).show()
   }
    fun startQuiz2(view: View){
        val button=findViewById<Button>(R.id.c2)
        val intent=Intent(this,QuizCategory2::class.java)
        startActivity(intent)
        finish()
        Toast.makeText(this,"Geography",Toast.LENGTH_SHORT).show()
    }
    fun startQuiz3(view: View){
        val button=findViewById<Button>(R.id.c3)
        val intent=Intent(this,QuizCategory3::class.java)
        startActivity(intent)
        finish()
        Toast.makeText(this,"General Knowledge",Toast.LENGTH_SHORT).show()
    }
    fun startQuiz4(view: View){
        val button=findViewById<Button>(R.id.c4)
        val intent=Intent(this,QuizCategory4::class.java)
        startActivity(intent)
        finish()
        Toast.makeText(this,"History",Toast.LENGTH_SHORT).show()
    }
    fun startQuiz5(view: View){
        val button=findViewById<Button>(R.id.c5)
        val intent=Intent(this,QuizCategory5::class.java)
        startActivity(intent)
        Toast.makeText(this,"Technology",Toast.LENGTH_SHORT).show()
    }
    fun startQuiz6(view: View){
        val button=findViewById<Button>(R.id.c6)
        val intent=Intent(this,QuizCategory6::class.java)
        startActivity(intent)
        finish()
        Toast.makeText(this,"Sports",Toast.LENGTH_SHORT).show()
    }
    fun startQuiz7(view: View){
        val button=findViewById<Button>(R.id.c7)
        val intent=Intent(this,QuizCategory7::class.java)
        startActivity(intent)
        finish()
        Toast.makeText(this,"Entertainment",Toast.LENGTH_SHORT).show()
    }
    fun startQuiz8(view: View){
        val button=findViewById<Button>(R.id.c8)
        val intent=Intent(this,QuizCategory8::class.java)
        startActivity(intent)
        finish()
        Toast.makeText(this,"Music",Toast.LENGTH_SHORT).show()
    }
    fun startQuiz9(view: View){
        val button=findViewById<Button>(R.id.c9)
        val intent=Intent(this,QuizCategory9::class.java)
        startActivity(intent)
        finish()
        Toast.makeText(this,"Sneaker Culture",Toast.LENGTH_SHORT).show()
    }
    fun startQuiz10(view: View){
        val button=findViewById<Button>(R.id.c10)
        val intent=Intent(this,QuizCategory10::class.java)
        startActivity(intent)
        finish()
        Toast.makeText(this,"Politics",Toast.LENGTH_SHORT).show()
    }

    fun switchbwtweenfragments(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containFragment, fragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


}
