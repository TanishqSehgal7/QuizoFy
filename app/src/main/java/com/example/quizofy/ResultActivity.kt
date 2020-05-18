package com.example.quizofy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val ab: ActionBar?=supportActionBar
        if (ab!=null){
            ab.setBackgroundDrawable(getDrawable(R.drawable.actionbargrag))
        }
        Toast.makeText(this,"Tap Exit button to start browsing categories",Toast.LENGTH_SHORT).show()

        val bundle: Bundle? = intent.extras
        val corr: String? = bundle?.getString("correct")
        val wrng: String? = bundle?.getString("Wrong")
        val scor: String? = bundle?.getString("score")

        correctAns.setText("Correct: "+ corr)
        wrongAns.setText("Wrong: " + wrng)
        ScoreTotal.setText("Total Score: " + scor)

        closeBtn.setOnClickListener {
            exitTointroScreen()
        }

    }

    fun exitTointroScreen(){
        val intent =Intent(this,Main2ActivityIntroScreen::class.java)
        startActivity(intent)
        finish()
    }
}
