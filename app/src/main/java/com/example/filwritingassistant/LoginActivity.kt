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

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /** Initializing Variables **/
        val login = findViewById<Button>(R.id.loginButton)
        val noAccount = findViewById<TextView>(R.id.tvNoAcc)

        /** intent variable **/
        var intent : Intent?

        /**Making the "Sign Up" bold in the tvNoAcc**/
        val string = "Doesn't have an account? Signup here."
        val spannable = SpannableString(string)
        val startIndex = string.indexOf("Signup")
        val endIndex = startIndex + "Signup".length
        spannable.setSpan(StyleSpan(Typeface.BOLD), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        noAccount.text = spannable

    }
}