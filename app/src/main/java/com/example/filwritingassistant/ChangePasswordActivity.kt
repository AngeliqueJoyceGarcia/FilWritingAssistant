package com.example.filwritingassistant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var user : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)


        /** Getting the current user that was logged in to the system **/
        user = FirebaseAuth.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val database = FirebaseDatabase.getInstance().reference


        /** Change Password elements declaration **/
        val btnOldPassView = findViewById<ImageView>(R.id.btnOldPassView)
        val btnNewPassView = findViewById<ImageView>(R.id.btnNewPassView)
        val oldPass = findViewById<EditText>(R.id.edOldPass)
        val newPass = findViewById<EditText>(R.id.edNewPassword)
        val back = findViewById<TextView>(R.id.btnCancel)
        val save = findViewById<TextView>(R.id.btnSave)

        btnOldPassView.setOnClickListener {
            val inputType = oldPass.inputType
            if (inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT) {
                // Show password
                oldPass.inputType =
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or InputType.TYPE_CLASS_TEXT
            } else {
                // Hide password
                oldPass.inputType =
                    InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
            }
            // Move cursor to the end of the text
            oldPass.setSelection(oldPass.text.length)
        }



        btnNewPassView.setOnClickListener {
            val inputType = newPass.inputType
            if (inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT) {
                // Show password
                newPass.inputType =
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or InputType.TYPE_CLASS_TEXT
            } else {
                // Hide password
                newPass.inputType =
                    InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
            }
            // Move cursor to the end of the text
            oldPass.setSelection(newPass.text.length)

        }


        back.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        save.setOnClickListener {
            val enteredOldPass = oldPass.text.toString()
            val newEnteredPass = newPass.text.toString()
            val userRef = database.child("Profiles").child(currentUser!!.uid)

            // Reauthenticate the user before updating the password
            val credential = EmailAuthProvider.getCredential(currentUser.email!!, enteredOldPass)
            currentUser.reauthenticate(credential)
                .addOnCompleteListener { reauthTask ->
                    if (reauthTask.isSuccessful) {
                        // User has been successfully reauthenticated
                        // Proceed with updating the password

                        currentUser.updatePassword(newEnteredPass)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    // Password updated successfully in Firebase Authentication
                                    userRef.child("password").setValue(newEnteredPass)
                                        .addOnCompleteListener { innerTask ->
                                            if (innerTask.isSuccessful) {
                                                // Password updated successfully in Realtime Database
                                                Toast.makeText(
                                                    this@ChangePasswordActivity,
                                                    "Password changed successfully",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                                startActivity(Intent(this@ChangePasswordActivity, ProfileActivity::class.java))
                                                finish()
                                            } else {
                                                // Failed to update password in Realtime Database
                                                Toast.makeText(
                                                    this@ChangePasswordActivity,
                                                    "Failed to update password",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                } else {
                                    // Failed to update password in Firebase Authentication
                                    Toast.makeText(
                                        this@ChangePasswordActivity,
                                        "Failed to update password",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        // Failed to reauthenticate user
                        Toast.makeText(
                            this@ChangePasswordActivity,
                            "Old password is incorrect",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }






    }
}