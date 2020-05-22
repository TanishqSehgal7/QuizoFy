package com.example.quizofy


import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * A simple [Fragment] subclass.
 */
class AboutAppFragment : Fragment() {
    lateinit var bottomnav: BottomNavigationView

    @RequiresApi(Build.VERSION_CODES.N)
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

        val btnSend=view.findViewById<Button>(R.id.btnEmail)
        btnSend.setOnClickListener {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "tsgl7246@gmail.com", null))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body")
            startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }

        val linkedInbtn=view.findViewById<Button>(R.id.linked)
        linkedInbtn.setOnClickListener {
            val i = Intent()
            i.action = Intent.ACTION_VIEW
            i.data = Uri.parse("https://www.linkedin.com/in/tanishq-sehgal-a86bbb184")
            startActivity(i)
        }

        val githubBtn=view.findViewById<Button>(R.id.github)
        githubBtn.setOnClickListener {
            val i =Intent()
            i.action = Intent.ACTION_VIEW
            i.data = Uri.parse("https://github.com/TanishqSehgal7")
            startActivity(i)
        }


        return view
    }
    override fun onResume() {
        super.onResume()
        bottomnav.menu.findItem(R.id.aboutApp).isChecked = true
    }

}
