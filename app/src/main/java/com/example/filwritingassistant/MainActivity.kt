package com.example.filwritingassistant

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        // Check if the user has already opened the app
        val prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val firstTime = prefs.getBoolean("first_time", true)

        if (firstTime) {
            // Show the main activity if this is the first time the user opens the app
            setContentView(R.layout.activity_main)

            // Save the flag indicating that the app has been opened
            prefs.edit().putBoolean("first_time", false).apply()
        } else {
            // Check if the user is already logged in
            user = FirebaseAuth.getInstance()

            if (user.currentUser != null) {
                // Redirect to DashboardActivity if the user is already logged in
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Redirect to LoginActivity if the user is not logged in
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        val button = findViewById<ImageView>(R.id.btnNext)

        button.setOnClickListener {
            user = FirebaseAuth.getInstance()

            if (user.currentUser != null) {
                // Redirect to DashboardActivity if the user is already logged in
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
            } else {
                // Redirect to LoginActivity if the user is not logged in
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

    }
}
