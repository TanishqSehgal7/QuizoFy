package com.example.quizofy


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import kotlinx.android.synthetic.main.fragment_about_app.*

/**
 * A simple [Fragment] subclass.
 */
class AboutAppFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about_app, container, false)
        val iv = view.findViewById<ImageView>(R.id.iv)
        iv.setImageResource(R.drawable.quesmrk)
        return view
    }
}
