package com.example.quizofy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_category.*

/**
 * A simple [Fragment] subclass.
 */
class CategoryFragment : Fragment() {
    var mCtx:Context? = null
    lateinit var c1:Button
    lateinit var c2:Button
    lateinit var c3:Button
    lateinit var c4:Button
    lateinit var c5:Button
    lateinit var c6:Button
    lateinit var c7:Button
    lateinit var c8:Button
    lateinit var c9:Button
    lateinit var c10:Button
    lateinit var bottomnav:BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        bottomnav = activity?.findViewById(R.id.bottomNavigation)!!
        c1 = view.findViewById(R.id.c1)
        c2 = view.findViewById(R.id.c2)
        c3 = view.findViewById(R.id.c3)
        c4 = view.findViewById(R.id.c4)
        c5 = view.findViewById(R.id.c5)
        c6 = view.findViewById(R.id.c6)
        c7 = view.findViewById(R.id.c7)
        c8 = view.findViewById(R.id.c8)
        c9 = view.findViewById(R.id.c9)
        c10 = view.findViewById(R.id.c10)
//        runAnimationOnCategoryTv()
        c1.setOnClickListener{
            val intent = Intent(mCtx, QuizCategory1::class.java)
            startActivity(intent)
        }
        c2.setOnClickListener{
            val intent = Intent(mCtx, QuizCategory2::class.java)
            startActivity(intent)
        }
        c3.setOnClickListener {
            val intent = Intent(mCtx, QuizCategory3::class.java)
            startActivity(intent)
        }
        c4.setOnClickListener {
            val intent = Intent(mCtx, QuizCategory4::class.java)
            startActivity(intent)
        }
        c5.setOnClickListener {
            val intent = Intent(mCtx, QuizCategory5::class.java)
            startActivity(intent)
        }
        c6.setOnClickListener {
            val intent = Intent(mCtx, QuizCategory6::class.java)
            startActivity(intent)
        }
        c7.setOnClickListener {
            val intent = Intent(mCtx, QuizCategory7::class.java)
            startActivity(intent)
        }
        c8.setOnClickListener {
            val intent = Intent(mCtx, QuizCategory8::class.java)
            startActivity(intent)
        }
        c9.setOnClickListener {
            val intent = Intent(mCtx, QuizCategory9::class.java)
            startActivity(intent)
        }
        c10.setOnClickListener {
            val intent = Intent(mCtx, QuizCategory10::class.java)
            startActivity(intent)
        }

        return  view
    }
    fun runAnimationOnCategoryTv(){
        val animCategory= AnimationUtils.loadAnimation(context,R.anim.fadein)
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
        bottomnav.startAnimation(animCategory)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mCtx = context;
    }

    override fun onResume() {
        super.onResume()
        bottomnav.menu.findItem(R.id.home).isChecked = true
    }

}
