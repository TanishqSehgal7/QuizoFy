package com.example.quizofy


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_about_app.*

/**
 * A simple [Fragment] subclass.
 */
class AboutAppFragment : Fragment() {
    lateinit var bottomnav: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about_app, container, false)
        bottomnav = activity?.findViewById(R.id.bottomNavigation)!!
        val iv = view.findViewById<ImageView>(R.id.iv)
        iv.bringToFront()
        iv.setImageResource(R.drawable.quesmrk)
        return view
    }
    override fun onResume() {
        super.onResume()
        bottomnav.menu.findItem(R.id.aboutApp).isChecked = true
    }
}
