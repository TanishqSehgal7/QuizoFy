package com.example.quizofy

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity

class SaveSharedPref : AppCompatActivity() {

    object SaveSharedPrefernce {
        const val CHECKTHEME = "checkTheme"
        fun getSharedPreferences(ctx: Context?): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(ctx)
        }

        fun setChecktheme(context: Context?, check: Int) {
            val editor = getSharedPreferences(context).edit()
            editor.putInt(CHECKTHEME, check)
            editor.apply()
        }

        fun getChecktheme(context: Context?): Int {
            return getSharedPreferences(context)
                .getInt(CHECKTHEME, 0)
        }
    }

}