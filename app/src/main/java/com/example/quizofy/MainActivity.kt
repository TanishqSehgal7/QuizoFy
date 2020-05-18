package com.example.quizofy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_quiz_category1.*
import kotlinx.android.synthetic.main.activity_quiz_category10.*
import kotlinx.android.synthetic.main.activity_quiz_category2.*
import kotlinx.android.synthetic.main.activity_quiz_category3.*
import kotlinx.android.synthetic.main.activity_quiz_category4.*
import kotlinx.android.synthetic.main.activity_quiz_category5.*
import kotlinx.android.synthetic.main.activity_quiz_category6.*
import kotlinx.android.synthetic.main.activity_quiz_category7.*
import kotlinx.android.synthetic.main.activity_quiz_category8.*
import kotlinx.android.synthetic.main.activity_quiz_category9.*
import kotlinx.android.synthetic.main.fragment_themechange.*

const val EXTRA_MESSAGE = "com.example.quizofy.MESSEGE"


class MainActivity : AppCompatActivity(){
    val categoryFragment = CategoryFragment()
    val notificationsFragment = NotificationsFragment()
    val aboutAppFragment = AboutAppFragment()

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    switchbwtweenfragments(categoryFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.notify -> {
                    switchbwtweenfragments(notificationsFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.Theme -> {
                    val i=Intent(this,ThemeActivity::class.java)
                    startActivity(i)
//                    switchbwtweenfragments(ThemeFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.aboutApp -> {
                    switchbwtweenfragments(aboutAppFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false

        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        switchbwtweenfragments(categoryFragment)


//        val ab:ActionBar?=supportActionBar
//        if (ab != null) {
//            ab.setBackgroundDrawable(getDrawable(R.drawable.actionbargrag))
//        }

        runAnimationOnCategoryTv()

        Log.d("QuizCategory1","Main activity oncreate called")
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        FirebaseMessaging.getInstance().isAutoInitEnabled=true


    }


    fun switchbwtweenfragments(fragment: Fragment) {

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containFragment, fragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.isAddToBackStackAllowed
        Log.d("QuizCategory1","frag is loaded")
        fragmentTransaction.commit()

    }




}
