    package com.example.parstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.parse.ParseUser

    class LoginActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)
            // check if there's a user logged in
            //if there is, take them to MainActivity
            if (ParseUser.getCurrentUser() != null){
                goToMainActivity()
            }

            findViewById<Button>(R.id.login_button).setOnClickListener {
                val username = findViewById<EditText>(R.id.et_username).text.toString()
                val password = findViewById<EditText>(R.id.et_password).text.toString()
                LoginUser(username, password)
            }
            findViewById<Button>(R.id.signupBtn).setOnClickListener {
                val username = findViewById<EditText>(R.id.et_username).text.toString()
                val password = findViewById<EditText>(R.id.et_password).text.toString()
                signUpUser(username, password)
            }
        }
        private fun signUpUser(username: String, password: String){
            // Create the ParseUser
            val user = ParseUser()

// Set fields for the user to be created
            user.setUsername(username)
            user.setPassword(password)

            user.signUpInBackground { e ->
                if (e == null) {
                    Toast.makeText(this,"Successfully signed up!",Toast.LENGTH_SHORT).show()
                    // Hooray! Let them use the app now.
                    //navigate user to mainactivity and show a toast to indicate
                    //user signed up
                } else {
                    e.printStackTrace()
                    Toast.makeText(this,"Sign up failed",Toast.LENGTH_SHORT).show()
                    //show a toast to tell user sign up failed
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        }


        private fun LoginUser(username: String, password: String) {
            ParseUser.logInInBackground(
                username, password, ({ user, e ->
                    if (user != null) {
                        Log.i(TAG, "Successfully logged in user")
                        goToMainActivity()
                        finish()

                    } else {
                        e.printStackTrace()
                        Toast.makeText(this,"Error Logging in", Toast.LENGTH_SHORT).show()

                    }
                })

            )

        }

        private fun goToMainActivity(){
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
        companion object {
            const val TAG = "LoginActivity"
        }
    }


    
