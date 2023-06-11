package com.example.filwritingassistant

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Calendar

class ProfileActivity : AppCompatActivity() {

    private lateinit var user : FirebaseAuth
    lateinit var bday : EditText
    private lateinit var userEmail : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        /** Profile Activity Elements **/
        val btncancel = findViewById<TextView>(R.id.btnCancel)
        val btnsave = findViewById<TextView>(R.id.btnSave)
        val emailAddress = findViewById<EditText>(R.id.edEmail)
        val userName = findViewById<EditText>(R.id.edName)
        val org = findViewById<EditText>(R.id.edOrganization)
        val changePass = findViewById<LinearLayout>(R.id.changePassHolder)
        val pass = findViewById<EditText>(R.id.tvPassword)

        /** Getting the current user that was logged in to the system **/
        user = FirebaseAuth.getInstance()
        val userCurrent = FirebaseAuth.getInstance().currentUser
        val database = FirebaseDatabase.getInstance().reference


        /** Getting the current user that was logged in to the system **/
        val email = userCurrent?.email
        if (email != null) {
            database.child("Profiles").orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (profileSnapshot in dataSnapshot.children) {
                            val profile = profileSnapshot.value as Map<*, *>
                            val name = profile["name"] as String
                            val email = profile["email"] as String
                            val organization = profile["organization"] as String
                            val userPass = profile["password"] as String

                            if (name.isNotEmpty()) {
                                userName.setText(name)
                            }

                            if (email.isNotEmpty()) {
                                emailAddress.setText(email)
                            }

                            if (organization.isNotEmpty()) {
                                org.setText(organization)
                            }

                            if (userPass.isNotEmpty()) {
                                pass.setText(userPass)
                            }

                            runOnUiThread {
                                userName.setText(name)
                                emailAddress.setText(email)
                                org.setText(organization)
                            }

                            // Initialize userEmail variable
                            userEmail = email

                            break
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            this@ProfileActivity,
                            "Failed to retrieve data from database",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

        }

        btncancel.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        emailAddress.setOnClickListener {
            Toast.makeText(this, "Changing Email is not yet supported", Toast.LENGTH_SHORT).show()
        }

        btnsave.setOnClickListener {
            val name = userName.text.toString().trim()
            val birthday = bday.text.toString().trim()
            val organization = org.text.toString().trim()

            if (name.isEmpty() || birthday.isEmpty() || organization.isEmpty()) {
                Toast.makeText(this, "Please fill up all fields", Toast.LENGTH_SHORT).show()
            } else {
                val database = FirebaseDatabase.getInstance().reference
                val query = database.child("Profiles").orderByChild("email").equalTo(userEmail)

                query.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (profileSnapshot in dataSnapshot.children) {
                            val savedName = profileSnapshot.child("name").value as String
                            val savedBirthday = profileSnapshot.child("birthday").value as String
                            val savedOrganization =
                                profileSnapshot.child("organization").value as String

                            if (name == savedName && birthday == savedBirthday && organization == savedOrganization) {
                                // User details haven't changed
                                Toast.makeText(
                                    this@ProfileActivity,
                                    "User details haven't changed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                // Update user details in Firebase
                                profileSnapshot.ref.child("name").setValue(name)
                                profileSnapshot.ref.child("birthday").setValue(birthday)
                                profileSnapshot.ref.child("organization").setValue(organization)
                                Toast.makeText(
                                    this@ProfileActivity,
                                    "Profile updated successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Go to Dashboard activity
                                val intent =
                                    Intent(this@ProfileActivity, DashboardActivity::class.java)
                                startActivity(intent)
                                finish()
                            }

                            break
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            this@ProfileActivity,
                            "Failed to update profile",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }

        changePass.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }

    }
}