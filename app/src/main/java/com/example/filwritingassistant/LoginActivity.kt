package com.example.filwritingassistant

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        user = FirebaseAuth.getInstance()

        /** Initializing Variables **/
        val login = findViewById<Button>(R.id.loginButton)
        val noAccount = findViewById<TextView>(R.id.tvNoAcc)
        val forgotPass = findViewById<TextView>(R.id.tvForgotPass)

        /** intent variable **/
        var intent: Intent?

        /**Making the "Sign Up" bold in the tvNoAcc**/
        val string = "Doesn't have an account? Signup"
        val spannable = SpannableString(string)
        val startIndex = string.indexOf("Signup")
        val endIndex = startIndex + "Signup".length
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        noAccount.text = spannable

        /** Login Button Event **/
        login.setOnClickListener {

            val email = findViewById<TextView>(R.id.email).text.toString()
            val password = findViewById<TextView>(R.id.password).text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                user.signInWithEmailAndPassword(email, password).addOnCompleteListener { mTask ->
                    if (mTask.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Login successful",
                            Toast.LENGTH_LONG
                        ).show()

                        startActivity(Intent(this, DashboardActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, mTask.exception!!.message, Toast.LENGTH_LONG).show()
                    }
                }

            } else {
                Toast.makeText(this, "Email and/or Password cannot be empty", Toast.LENGTH_LONG)
                    .show()
            }
        }

        noAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        forgotPass.setOnClickListener {

            val email = findViewById<TextView>(R.id.email).text.toString().trim()

            if (email.isNotEmpty()) {
                user.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Password reset email sent. Please check your inbox.", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Failed to send password reset email. Please try again.", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please enter your email address.", Toast.LENGTH_LONG).show()
            }
        }


    }

    



}