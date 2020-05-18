package com.example.quizofy


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * A simple [Fragment] subclass.
 */
class NotificationsFragment : Fragment() {
    lateinit var bottomnav: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bottomnav = activity?.findViewById(R.id.bottomNavigation)!!
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }
    override fun onResume() {
        super.onResume()
        bottomnav.menu.findItem(R.id.notify).isChecked = true
    }


}
