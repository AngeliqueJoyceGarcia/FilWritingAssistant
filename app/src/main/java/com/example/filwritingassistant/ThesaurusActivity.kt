package com.example.filwritingassistant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ThesaurusActivity : AppCompatActivity() {

    //for side menu
    private lateinit var user : FirebaseAuth
    lateinit var drawer: DrawerLayout


    lateinit var suggestionList: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thesaurus)

        //for dictionary
        val search = findViewById<EditText>(R.id.edSearch)
        val btnSearch2 = findViewById<ImageView>(R.id.btnSearch2)
        val searchedWord = findViewById<TextView>(R.id.tvSearchedWord2)
        val resultSynonyms = findViewById<TextView>(R.id.tvSynonyms)
        val resultAntonyms = findViewById<TextView>(R.id.tvAntonym)
        val synonyms = findViewById<TextView>(R.id.lblSynonyms)
        val antonyms = findViewById<TextView>(R.id.lblAntonyms)
        val holderS = findViewById<ConstraintLayout>(R.id.holderSynonym)
        val holderA = findViewById<ConstraintLayout>(R.id.holderAntonym)
        val line = findViewById<View>(R.id.line4)
        suggestionList = findViewById(R.id.suggestionList)

        //for side menu
        val sidemenu = findViewById<ImageView>(R.id.btnSideMenu3)
        val home = findViewById<LinearLayout>(R.id.home)
        val profile = findViewById<LinearLayout>(R.id.profile)
        val aboutUs = findViewById<LinearLayout>(R.id.about)
        val logout = findViewById<LinearLayout>(R.id.logout)
        val database = FirebaseDatabase.getInstance().reference
        val userName = findViewById<TextView>(R.id.tvname)
        val emailAddress = findViewById<TextView>(R.id.tvemail)


        drawer = findViewById(R.id.parentThesaurus)

        /** Getting the current user that was logged in to the system **/
        user = FirebaseAuth.getInstance()
        val userCurrent = FirebaseAuth.getInstance().currentUser

        /** Getting the current user that was logged in to the system **/
        if(userCurrent!=null){
            val email =userCurrent.email

            if (email != null){
                database.child("Profiles").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (profileSnapshot in dataSnapshot.children) {
                            val profile = profileSnapshot.value as Map<*, *>
                            val name = profile["name"] as String
                            val email = profile["email"] as String

                            Log.d("TAG", "Name: $name")
                            Log.d("TAG", "Email: $email")

                            if (name.isNotEmpty()) {
                                userName.text = name
                            }

                            if (email.isNotEmpty()) {
                                emailAddress.text = email
                            }

                            runOnUiThread {
                                userName.text = name
                                emailAddress.text = email
                            }
                            break
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@ThesaurusActivity, "Failed to retrieve data from database", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        home.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        profile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        aboutUs.setOnClickListener {
            startActivity(Intent(this, AppInfoActivity::class.java))
        }

        logout.setOnClickListener {
            user.signOut()
            Toast.makeText(this, "Successfully logged out :)", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        sidemenu.setOnClickListener {
            openDrawer(drawer)
        }


        search.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                // This method is called after the user changes the text
                val searchText = s.toString().trim()

                //query the database for words that start with the search text
                val query = database.child("Thesaurus")
                    .orderByChild("Word")
                    .startAt(searchText)
                    .endAt(searchText + "\uf8ff")

                query.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        // Create a list of suggestions
                        val suggestions = mutableListOf<String>()
                        for (wordSnapshot in snapshot.children) {
                            val word = wordSnapshot.child("Word").value.toString()
                            suggestions.add(word)
                        }

                        // Display the suggestions in a list view
                        val adapter = ArrayAdapter<String>(
                            applicationContext,
                            android.R.layout.simple_list_item_1,
                            suggestions
                        )
                        suggestionList.adapter = adapter

                        // Show the suggestion list if there are suggestions to show
                        suggestionList.visibility = if (suggestions.isEmpty()) View.GONE else View.VISIBLE

                        // Update the visibility of var1 based on the visibility of suggestionList
                        synonyms.visibility = if (suggestionList.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                        searchedWord.visibility = if (suggestionList.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                        antonyms.visibility = if (suggestionList.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                        holderA.visibility = if (suggestionList.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                        holderS.visibility = if (suggestionList.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                        line.visibility = if (suggestionList.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                    }

                    override fun onCancelled(error: DatabaseError) {
                        //handle any errors
                        Log.e("ThesaurusActivity", "Error querying database: ${error.message}")
                    }
                })
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This method is called before the user changes the text
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                // This method is called when the user changes the text
            }
        })

        // Add an OnItemClickListener to the suggestionList
        suggestionList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // Get the selected word from the suggestionList
            val selectedWord = parent.getItemAtPosition(position).toString()

            // Query the database for the selected word
            val query = database.child("Thesaurus")
                .orderByChild("Word")
                .equalTo(selectedWord)

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Retrieve the details for the selected word
                    for (wordSnapshot in snapshot.children) {
                        val word = wordSnapshot.child("Word").value.toString()
                        val antonyms = wordSnapshot.child("Antonym").value.toString()
                        val synonyms = wordSnapshot.child("Synonym").value.toString()

                        // Set the corresponding values to the text views
                        searchedWord.text = word
                        resultAntonyms.text = antonyms
                        resultSynonyms.text = synonyms
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle any errors
                    Log.e("DictionaryActivity", "Error querying database: ${error.message}")
                }
            })

            suggestionList.visibility = View.GONE
            synonyms.visibility = View.VISIBLE
            antonyms.visibility = View.VISIBLE
            searchedWord.visibility = View.VISIBLE
            holderA.visibility = View.VISIBLE
            holderS.visibility = View.VISIBLE
            line.visibility = View.VISIBLE
        }

        //set an event listener for the search button
        btnSearch2.setOnClickListener {
            val searchText = search.text.toString().trim()

            //query the database for words that start with the search text
            val query = database.child("Thesaurus")
                .orderByChild("Word")
                .startAt(searchText)
                .endAt(searchText + "\uf8ff")

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //check if any matching words were found
                    if (snapshot.exists()) {
                        //get the first matching word
                        val wordSnapshot = snapshot.children.first()

                        //get the word details
                        val word = wordSnapshot.child("Word ").value.toString()
                        val antonyms = wordSnapshot.child("Antonym").value.toString()
                        val synonyms = wordSnapshot.child("Synonym").value.toString()


                        //display the word details in the UI
                        searchedWord.text = word
                        resultAntonyms.text = antonyms
                        resultSynonyms.text = synonyms
                    } else {
                        //no matching words found
                        searchedWord.text = "No results found"
                        resultAntonyms.text = ""
                        resultSynonyms.text = ""
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    //handle any errors
                    Log.e("ThesaurusActivity", "Error querying database: ${error.message}")
                }
            })
        }

    }

    fun openDrawer(drawer:DrawerLayout){
        drawer.openDrawer(GravityCompat.START)

    }

    fun closeDrawer(drawer:DrawerLayout){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        }
    }

    override fun onPause(){
        super.onPause()
        closeDrawer(drawer)
    }
}