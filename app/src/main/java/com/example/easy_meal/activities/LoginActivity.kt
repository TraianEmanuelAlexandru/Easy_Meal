package com.example.easy_meal.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.easy_meal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var forgot_pass: TextView
    private lateinit var sign_up: TextView
    private lateinit var login: Button
    private lateinit var email: TextView
    private lateinit var pass: TextView
    private var mAuth: FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        forgot_pass = findViewById(R.id.loginForgotPassTV)
        sign_up = findViewById(R.id.loginSignUpTv)
        login = findViewById(R.id.loginBtn)
        email = findViewById(R.id.loginEmailEt)
        pass = findViewById(R.id.loginPasswordEt)
        forgot_pass.setOnClickListener(this)
        sign_up.setOnClickListener(this)
        login.setOnClickListener(this)
    }


    override fun onClick(v: View?) {

        if ( v===sign_up) {
            val startIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(startIntent)
        }
        else if ( v===forgot_pass){
            val startIntent = Intent(this@LoginActivity, ResetPasswordActivity::class.java)
            startActivity(startIntent)
        }
        else if ( v===login) {

            val user_email = email.text.toString()
            val user_password = pass.text.toString()
            try {
                if (user_email.isEmpty() || user_password.isEmpty()) {
                    throw Exception("All fields must be filled")
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(user_email).matches()) {
                    throw Exception("Invalid Email ")
                }
                loginUser(user_email, user_password)
                } catch (e: Exception) {

                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun loginUser(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task->
                if (task.isSuccessful) {
                    val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                    startActivity(mainIntent)
                    finish()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this@LoginActivity, "Invalid Password", Toast.LENGTH_LONG)
                            .show()
                    } else if (task.exception is FirebaseAuthInvalidUserException) {
                        Toast.makeText(this@LoginActivity, "Email not exist", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
    }

}