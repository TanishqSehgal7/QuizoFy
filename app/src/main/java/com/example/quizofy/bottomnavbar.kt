package com.example.quizofy

import android.content.Intent
import android.net.wifi.hotspot2.pps.HomeSp
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class bottomnavbar : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {item->
        when(item.itemId){
            R.id.home ->{

                Toast.makeText(this,"Navigating to Home",Toast.LENGTH_SHORT).show()
                val intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.notify ->{
                switchbwtweenfragments(NotificationsFragment())
                Toast.makeText(this,"Showing Notifications",Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.Score ->{
                switchbwtweenfragments(ScoreFragment())
                Toast.makeText(this,"Your Scores",Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.aboutApp->{
                switchbwtweenfragments(AboutAppFragment())
               Toast.makeText(this,"Developer Details",Toast.LENGTH_SHORT).show()
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

    private fun switchbwtweenfragments(fragment: Fragment){
        val fragmentTransaction=supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containFragment,fragment)
        fragmentTransaction.commit()
    }
}