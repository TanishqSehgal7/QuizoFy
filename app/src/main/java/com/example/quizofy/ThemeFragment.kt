package com.example.quizofy


import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_theme.*
import kotlinx.android.synthetic.main.fragment_themechange.*

/**
 * A simple [Fragment] subclass.
 */
class ThemeFragment : Fragment() {
    var selection: String? = null
    var checked_item = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("ThemeFrag","frag opened")
        val view = inflater.inflate(R.layout.fragment_themechange, container, false)

        // Inflate the layout for this fragment
        val theme = activity?.applicationContext?.resources?.getStringArray(R.array.Theme)
         changeTheme.setOnClickListener{
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Choose Theme")
            builder.setSingleChoiceItems(R.array.Theme, SaveSharedPref.SaveSharedPrefernce.getChecktheme(activity?.applicationContext)
            ) { dialog, which -> selection = theme?.get(which)
                checked_item = which
            }
            builder.setPositiveButton("OK"){dialog, which ->
                if (selection==null){
                    checked_item = SaveSharedPref.SaveSharedPrefernce.getChecktheme(activity?.applicationContext)
                    selection = theme?.get(checked_item)
                }
                when(selection){
                    "System Default"->AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    "Dark"-> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    "Light"->AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                SaveSharedPref.SaveSharedPrefernce.setChecktheme(activity?.applicationContext,checked_item)
            }
            builder.setNegativeButton("Cancel"){dialog, which -> dialog.dismiss() }
            val diag = builder.create()
            diag.show()
        }

        return view
    }



    override fun onResume() {
        super.onResume()
        bottomNavigation.menu.findItem(R.id.Theme).isChecked = true
    }

}

