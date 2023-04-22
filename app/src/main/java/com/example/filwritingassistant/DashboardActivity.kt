package com.example.filwritingassistant

import android.content.Intent
import android.content.Intent.ACTION_MAIN
import android.content.Intent.CATEGORY_HOME
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.service.voice.VoiceInteractionSession.VisibleActivityCallback
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage


class DashboardActivity : AppCompatActivity() {

    lateinit var drawer: DrawerLayout
    private lateinit var user : FirebaseAuth
    private lateinit var worksListView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val imagerecog = findViewById<LinearLayout>(R.id.imagerecog)
        val editor = findViewById<LinearLayout>(R.id.texteditor)
        val sidemenu = findViewById<ImageView>(R.id.btnsidemenu)
        val home = findViewById<LinearLayout>(R.id.home)
        val profile = findViewById<LinearLayout>(R.id.profile)
        val userName = findViewById<TextView>(R.id.tvname)
        val emailAddress = findViewById<TextView>(R.id.tvemail)
        val aboutUs = findViewById<LinearLayout>(R.id.about)
        val logout = findViewById<LinearLayout>(R.id.logout)
        val simulation = findViewById<LinearLayout>(R.id.simulation)
        val tutorial = findViewById<LinearLayout>(R.id.tutorial)
        val thesaurus = findViewById<LinearLayout>(R.id.thesaurus)
        val dictionary = findViewById<LinearLayout>(R.id.dictionary)
        val database = FirebaseDatabase.getInstance().reference

        drawer = findViewById(R.id.parent)

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
                        Toast.makeText(this@DashboardActivity, "Failed to retrieve data from database", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        /**Loading works**/
        viewWorks()

        /**for automatic switching of cardviews**/
        switchImageView()

        sidemenu.setOnClickListener {
            openDrawer(drawer)
        }

        home.setOnClickListener {
            recreate()
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


        imagerecog.setOnClickListener {
            startActivity(Intent(this,ImageRecognition::class.java))
        }

        editor.setOnClickListener {
            startActivity(Intent(this,TextEditor::class.java))
        }

        simulation.setOnClickListener {
            startActivity(Intent(this,SimulationActivity::class.java))
        }

        tutorial.setOnClickListener {
            startActivity(Intent(this,TutorialActivity::class.java))
        }

        thesaurus.setOnClickListener {
            startActivity(Intent(this,ThesaurusActivity::class.java))
        }

        dictionary.setOnClickListener {
            startActivity(Intent(this,DictionaryActivity::class.java))
        }


    }

    private fun switchImageView() {
        val cardview1 = findViewById<ImageView>(R.id.cvappinfo1)
        val cardview2 = findViewById<ImageView>(R.id.cvappinfo2)

        // Check which ImageView is currently visible
        val isVisible1 = cardview1.visibility == View.VISIBLE

        // Hide the currently visible ImageView and show the other one
        if (isVisible1) {
            cardview1.visibility = View.GONE
            cardview2.visibility = View.VISIBLE
        } else {
            cardview1.visibility = View.VISIBLE
            cardview2.visibility = View.GONE
        }

        // Call this function again after a 2-second delay
        Handler(Looper.getMainLooper()).postDelayed({
            switchImageView()
        }, 3000)
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

    override fun onBackPressed() {
        val startMain = Intent(ACTION_MAIN)
        startMain.addCategory(CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
        finish()
    }

    fun viewWorks(){
        // Get a reference to the Firebase Storage instance and to the current user's UID
        val storage = FirebaseStorage.getInstance()
        val uid = user.currentUser?.uid ?: return

        // Create a reference to the 'works' folder in the current user's storage
        val worksRef = storage.reference.child("users").child(uid).child("works")

        worksListView = findViewById(R.id.listWorks)

        // Add a listener to the 'works' folder reference to check if there are any files in the folder
        worksRef.listAll().addOnSuccessListener { listResult ->
            val fileNames = mutableListOf<String>()
            val noWork = findViewById<TextView>(R.id.tvnoWork)

            // If there are no files in the folder, show a message in the list view
            if (listResult.items.isEmpty()) {
                noWork.visibility = View.VISIBLE
                noWork.text = "No Work/s Found"
            } else {
                noWork.visibility = View.GONE
            }

            // If there are files in the folder, retrieve their names and store them in a list
            listResult.items.forEach { item ->
                val fileName = item.name
                fileNames.add(fileName)
            }

            // Create an adapter for the list view and set it to the 'works' list view
            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileNames)
            worksListView.adapter = adapter
        }
    }



}