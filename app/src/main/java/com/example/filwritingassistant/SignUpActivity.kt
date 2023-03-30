package com.example.filwritingassistant

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text
import java.util.*

class SignUpActivity : AppCompatActivity() {

    lateinit var bday: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val haveAccount = findViewById<TextView>(R.id.tvHaveAcc)
        val haveAccount2 = findViewById<TextView>(R.id.tvHaveAcc2)
        val next = findViewById<Button>(R.id.btnNext)
        val layout1 = findViewById<LinearLayout>(R.id.layout1)
        val layout2 = findViewById<LinearLayout>(R.id.layout2)

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
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // User was successfully created
                                layout1.visibility = View.GONE
                                layout2.visibility = View.VISIBLE
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



    }

    /**shows the date picker in the birthdate textview and set the constraint (max day = yesterday)**/
    private fun showDatePickerDialog() {
        // Get the current date as default
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Set the DatePickerDialog maximum date to today's date
        val datePickerDialog = DatePickerDialog(
            this,
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

        // Set the DatePickerDialog maximum date to today's date
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 1000 // Subtract a second from the current time to exclude the current date

        // Show the DatePickerDialog
        datePickerDialog.show()
    }

}