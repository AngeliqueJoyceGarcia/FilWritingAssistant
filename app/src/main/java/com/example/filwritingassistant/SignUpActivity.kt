package com.example.filwritingassistant

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import org.w3c.dom.Text
import java.util.*

class SignUpActivity : AppCompatActivity() {

    lateinit var bday: TextView
    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val haveAccount = findViewById<TextView>(R.id.tvHaveAcc)
        val haveAccount2 = findViewById<TextView>(R.id.tvHaveAcc2)
        val next = findViewById<Button>(R.id.btnNext)
        val layout1 = findViewById<LinearLayout>(R.id.layout1)
        val layout2 = findViewById<LinearLayout>(R.id.layout2)
        val submit = findViewById<Button>(R.id.btnSignup)


        bday = findViewById<TextView>(R.id.birthday)

        /**Making the "Sign Up" bold in the tvNoAcc**/
        val string = "Already have an account? Sign in"
        val spannable = SpannableString(string)
        val startIndex = string.indexOf("Sign in")
        val endIndex = startIndex + "Sign in".length
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        haveAccount.text = spannable
        haveAccount2.text = spannable

        /**user has acc event**/
        haveAccount.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        haveAccount2.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        next.setOnClickListener {
            val email = findViewById<TextView>(R.id.email).text.toString()
            val password = findViewById<TextView>(R.id.password).text.toString()
            val cpassword = findViewById<TextView>(R.id.confirmPassword).text.toString()


            /**checks if initial user details are complete**/
            if (email.isNotEmpty() && password.isNotEmpty() && cpassword.isNotEmpty()) {
                /** runs if password is the same with confirm password**/
                if (password == cpassword){
                    FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val signInMethods = task.result?.signInMethods ?: emptyList<String>()
                                if (signInMethods.isEmpty()) {
                                    // Email does not exist in Firebase Authentication, do something
                                    layout1.visibility = View.GONE
                                    layout2.visibility = View.VISIBLE
                                } else {
                                    // Email already exists in Firebase Authentication, show error message
                                    Toast.makeText(this, "Email already exists", Toast.LENGTH_LONG).show()
                                }
                            } else {
                                // An error occurred, show a toast message with the error
                                Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_LONG).show()
                            }
                        }

                } else {
                    Toast.makeText(
                        this,
                        "Passwords do not match",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            /**runs if details are not complete**/
            else {
                Toast.makeText(
                    this,
                    "User details is/are incomplete. Please try again.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        bday.setOnClickListener {
            showDatePickerDialog()
        }

        submit.setOnClickListener {
            val name = findViewById<TextView>(R.id.name)
            val organization = findViewById<TextView>(R.id.organization)

            if(name.text.toString().isNotEmpty() && bday.text.toString().isNotEmpty() && organization.text.toString().isNotEmpty()){
                    registerUser()
            }
           /**runs if details are not complete**/
            else {
                Toast.makeText(
                    this,
                    "User details is/are incomplete. Please try again.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    private fun registerUser() {
        val email = findViewById<TextView>(R.id.email).text.toString()
        val password = findViewById<TextView>(R.id.password).text.toString()
        val name = findViewById<TextView>(R.id.name).text.toString()
        val organization = findViewById<TextView>(R.id.organization).text.toString()
        val bday = findViewById<TextView>(R.id.birthday).text.toString()
        user = FirebaseAuth.getInstance()

        /** Creating Users in Firebase**/
        user.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(SignUpActivity()){task->
                /** will redirect to login activity after successful user registration**/
                if (task.isSuccessful){
                    Toast.makeText(this, "Sign up successful, please login to your account", Toast.LENGTH_LONG).show()

                    /** Adding User Details to Firebase**/
                    val database = FirebaseDatabase.getInstance().reference
                    val key = database.child("Profiles").push().key
                    val registerUser = Users(email,password,name,bday,organization)

                    if (key != null){
                        database.child("Profiles").child(key).setValue(registerUser)
                    }

                    val uid = user.currentUser?.uid
                    val storageRef = FirebaseStorage.getInstance().getReference("users/$uid/works") // set the directory path
                    storageRef.child("example.txt").putBytes(ByteArray(0)) // put your file into the directory


                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } else{
                    Toast.makeText(this, "Error $email $password $name $organization", Toast.LENGTH_LONG).show()
                }
            }
    }


    /**shows the date picker in the birthdate textview and set the constraint (max day = yesterday)**/
    private fun showDatePickerDialog() {
        // Get the current date as default
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Set the DatePickerDialog maximum date to one year before today's date
        val maxDate = calendar.apply { add(Calendar.YEAR, -1) }.timeInMillis
        val datePickerDialog = DatePickerDialog(
            ContextThemeWrapper(this, R.style.BrownDatePickerStyle),
            { _, year, monthOfYear, dayOfMonth ->
                // Set the selected date to the TextView
                val selectedDate = Calendar.getInstance().apply {
                    set(year, monthOfYear, dayOfMonth)
                }
                val today = Calendar.getInstance()
                if (selectedDate.before(today)) {
                    val dateString = "$dayOfMonth/${monthOfYear + 1}/$year"
                    bday.text = dateString
                } else {
                    Toast.makeText(this, "Please select a date before today", Toast.LENGTH_SHORT).show()
                }
            },
            year,
            month,
            day
        )


        datePickerDialog.datePicker.maxDate = maxDate // Set the maximum date to one year before today's date

        // Show the DatePickerDialog
        datePickerDialog.show()
    }

}