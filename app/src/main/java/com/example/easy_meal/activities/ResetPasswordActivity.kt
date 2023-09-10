package com.example.easy_meal.activities

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.easy_meal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var reset: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        email = findViewById(R.id.resetEmailEt)
        reset = findViewById(R.id.resetBtn)
        reset.setOnClickListener(View.OnClickListener {
            val auth = FirebaseAuth.getInstance()
            val emailAddress = email.getText().toString()
            try {
                if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                    throw Exception("Invalid Email")
                } else {
                    auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this@ResetPasswordActivity,
                                    "Email sent",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else if (task.exception is FirebaseAuthInvalidUserException) {
                                Toast.makeText(
                                    this@ResetPasswordActivity,
                                    "Email not exist",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }
            } catch (e: Exception) {
                Toast.makeText(this@ResetPasswordActivity, e.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}