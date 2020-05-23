package com.example.quizofy

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_theme.*

class ThemeActivity : AppCompatActivity() {
    var selection: String? = null
    var checked_item = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme)

        val theme = applicationContext.resources.getStringArray(R.array.Theme)
        cngthm.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Choose Theme")
            builder.setSingleChoiceItems(R.array.Theme, SaveSharedPref.SaveSharedPrefernce.getChecktheme(applicationContext)
            ) { dialog, which -> selection = theme[which]
                checked_item = which
            }
            builder.setPositiveButton("OK"){dialog, which ->
                if (selection==null){
                    checked_item = SaveSharedPref.SaveSharedPrefernce.getChecktheme(applicationContext)
                    selection = theme[checked_item]
                }
                when(selection){
                    "System Default"->AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    "Dark"-> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    "Light"->AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                SaveSharedPref.SaveSharedPrefernce.setChecktheme(applicationContext,checked_item)
            }
            builder.setNegativeButton("Cancel"){dialog, which -> dialog.dismiss() }
            val diag = builder.create()
            diag.show()
        }
    }
}
