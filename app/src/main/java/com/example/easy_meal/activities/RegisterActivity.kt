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
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private var mAuth: FirebaseAuth = Firebase.auth
    private lateinit var register: Button
    private lateinit var email: TextView
    private lateinit var pass: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        register = findViewById(R.id.registerBtn)
        email = findViewById(R.id.registerEmailEt)
        pass = findViewById(R.id.registerPasswordEt)
        register.setOnClickListener(this)



}
  override fun onClick(v: View?) {

  if ( v===register ) {
      val user_email = email.text.toString()
      val user_password = pass.text.toString()
      try {
          if ( user_email.isEmpty() || user_password.isEmpty()) {
              throw Exception("All fields must be fille")
          }
          if ( user_password.length < 6){
              Toast.makeText(this@RegisterActivity, "password too short", Toast.LENGTH_SHORT).show()
          }
          if (!Patterns.EMAIL_ADDRESS.matcher(user_email).matches()){
              throw Exception("Invalid email")
          } else {
          registerUser(user_email, user_password)

          }
      } catch (e: Exception) {
          Toast.makeText(this@RegisterActivity, "Login errato", Toast.LENGTH_LONG).show()
          }
      }
  }

  private fun registerUser(email: String, password: String){
      mAuth.createUserWithEmailAndPassword(email, password)
          .addOnCompleteListener { task->
              if (task.isSuccessful) {
                  val mainIntent = Intent(this@RegisterActivity, MainActivity::class.java)
                  mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                  Toast.makeText(this@RegisterActivity, "Ti sei registrato correttamente", Toast.LENGTH_SHORT).show()
                  startActivity(mainIntent)
                  finish()
              }
          }.addOnFailureListener { e ->
              if ( e is FirebaseAuthUserCollisionException) {
                  Toast.makeText(this@RegisterActivity, "Email already exists", Toast.LENGTH_SHORT).show()
              }
          }
  }
}



