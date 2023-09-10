package com.example.easy_meal.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.easy_meal.fragments.FavoritesFragment
import com.example.easy_meal.fragments.HomeFragment
import com.example.easy_meal.R
import com.example.easy_meal.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFavoriteFragment(HomeFragment())

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener(navListener)
    }


    public override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if (currentUser == null) {
            sendToLogin()
        }
    }

    private fun sendToLogin() {
        val loginIntent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(loginIntent)
        finish() // The user can't come back to this page
    }


    @SuppressLint("SuspiciousIndentation")
    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
    var selectedFragment: Fragment?= null
        when (item.itemId) {
            R.id.nav_favorites -> selectedFragment = FavoritesFragment()
            R.id.nav_home -> selectedFragment = HomeFragment()
            R.id.nav_search -> selectedFragment = SearchFragment()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, selectedFragment!!).commit()
        true
    }

    private fun setFavoriteFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.fragment_container,fragment)
        commit()
    }
}