package com.example.filwritingassistant

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Image
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.io.File


class TextEditor : AppCompatActivity() {

    lateinit var texteditor : EditText
    private val readRequestCode = 0
    lateinit var drawer: DrawerLayout
    private var storageRef = FirebaseStorage.getInstance()
    private lateinit var textEditor: EditText

    //for side menu
    private lateinit var user : FirebaseAuth

    companion object {
        const val PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 1
        const val PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 2
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_editor)

        //for side menu
        val sidemenu = findViewById<ImageView>(R.id.btnSideMenu)
        val home = findViewById<LinearLayout>(R.id.home)
        val profile = findViewById<LinearLayout>(R.id.profile)
        val aboutUs = findViewById<LinearLayout>(R.id.about)
        val logout = findViewById<LinearLayout>(R.id.logout)
        val database = FirebaseDatabase.getInstance().reference
        val userName = findViewById<TextView>(R.id.tvname)
        val emailAddress = findViewById<TextView>(R.id.tvemail)


        drawer = findViewById(R.id.parenttexteditor)

        //for saving in cloud
        val savetoCloud = findViewById<ImageView>(R.id.btntocloud)
        textEditor = findViewById(R.id.et_text_editor)

        //for download in the device
        val downloadToDevice = findViewById<ImageView>(R.id.btntodevice)

        //for upload in the text editor
        val upload = findViewById<ImageView>(R.id.btnupload)

        //setting the recognized text to text editor
       texteditor = findViewById(R.id.et_text_editor)
        val text = intent.getStringExtra("fileContent")
        texteditor.setText(text)

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
                        Toast.makeText(this@TextEditor, "Failed to retrieve data from database", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        sidemenu.setOnClickListener {
            openDrawer(drawer)
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

        downloadToDevice.setOnClickListener {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
                // Permission is already granted
                showFileNameInputDialog()
            } else {
                // Permission is not yet granted, request it
                requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE)
            }

        }

        upload.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    arrayOf( android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_READ_EXTERNAL_STORAGE)
            } else {
                performFileSearch()
            }
        }

        savetoCloud.setOnClickListener {
            val data = textEditor.text.toString()
            user.currentUser?.let {
                try {
                    val userWorksRef = storageRef.getReference("users/" + it.uid + "/works")

                    // Check if the saved text came from the listView
                    val fileName = intent.getStringExtra("filenames")
                    if (fileName != null) {
                        // Prompt the user if they want to overwrite the saved text or not
                        val builder = AlertDialog.Builder(this)
                        builder.setMessage("Do you want to overwrite the saved text?")
                            .setCancelable(false)
                            .setPositiveButton("Yes") { dialog, id ->
                                // Overwrite the saved text
                                userWorksRef.child(fileName).putBytes(data.toByteArray())
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
                                    }
                            }
                            .setNegativeButton("No") { dialog, id ->
                                // Save the text in a new entry in the listView
                                val filenameInputDialog = EditText(this)
                                filenameInputDialog.hint = "Enter file name"
                                val dialog = AlertDialog.Builder(this)
                                    .setTitle("Save As")
                                    .setView(filenameInputDialog)
                                    .setPositiveButton("Save") { dialog, which ->
                                        val newFileName = filenameInputDialog.text.toString()
                                        if (newFileName.isEmpty()) {
                                            Toast.makeText(this, "File name cannot be empty", Toast.LENGTH_LONG).show()
                                        } else {
                                            userWorksRef.child(newFileName).putBytes(data.toByteArray())
                                                .addOnSuccessListener {
                                                    Toast.makeText(this, "Saved as $newFileName", Toast.LENGTH_LONG).show()
                                                }
                                        }
                                    }
                                    .setNegativeButton("Cancel") { dialog, which -> }
                                    .create()
                                dialog.show()
                            }
                        val alert = builder.create()
                        alert.show()
                    } else {
                        // Save the text in a new entry in the listView
                        val filenameInputDialog = EditText(this)
                        filenameInputDialog.hint = "Enter file name"
                        val dialog = AlertDialog.Builder(this)
                            .setTitle("Save As")
                            .setView(filenameInputDialog)
                            .setPositiveButton("Save") { dialog, which ->
                                val newFileName = filenameInputDialog.text.toString()
                                if (newFileName.isEmpty()) {
                                    Toast.makeText(this, "File name cannot be empty", Toast.LENGTH_LONG).show()
                                } else {
                                    userWorksRef.child(newFileName).putBytes(data.toByteArray())
                                        .addOnSuccessListener {
                                            Toast.makeText(this, "Saved as $newFileName", Toast.LENGTH_LONG).show()
                                        }
                                }
                            }
                            .setNegativeButton("Cancel") { dialog, which -> }
                            .create()
                        dialog.show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
                }
            }
        }

        /** Checking the grammar of edit text content **/


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




    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, show the file name input dialog
                    showFileNameInputDialog()
                } else {
                    // Permission denied, display a message
                    Toast.makeText(this, "Permission to write to external storage is required to download files", Toast.LENGTH_LONG).show()
                }
            }
            PERMISSION_REQUEST_READ_EXTERNAL_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    performFileSearch()
                } else {
                    Toast.makeText(this, "Permission denied to read external storage", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    /**For uploading**/
    private fun performFileSearch() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "text/*"
        startActivityForResult(intent, readRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == readRequestCode && resultCode == Activity.RESULT_OK) {
            resultData?.data?.also { uri ->
                val inputStream = contentResolver.openInputStream(uri)
                val text = inputStream?.bufferedReader()?.use { it.readText() }
                textEditor.setText(text)
            }
        }
    }




    /**For download**/
    private fun showFileNameInputDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enter file name")

        val input = EditText(this)
        builder.setView(input)

        builder.setPositiveButton("OK") { _, _ ->
            val fileName = input.text.toString()
            if (fileName.isNotEmpty()) {
                saveFile(fileName)
            } else {
                Toast.makeText(this, "File name cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    private fun saveFile(fileName: String) {

        texteditor = findViewById(R.id.et_text_editor)

        val fileText = texteditor.text.toString()
        val file =
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "$fileName.txt")

        if (!file.exists()) {
            file.writeText(fileText)
            Toast.makeText(this,
                "Your file is downloaded successfully! Location: Downloads",
                Toast.LENGTH_LONG).show()
        } else {
            showOverwriteConfirmationDialog(file, fileText)
        }
    }

    private fun showOverwriteConfirmationDialog(file: File, fileText: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("File already exists")
        builder.setMessage("Do you want to overwrite the file with the same name?")

        builder.setPositiveButton("Yes") { _, _ ->
            file.writeText(fileText)
            Toast.makeText(this,
                "File is downloaded successfully!",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { dialog, _ -> dialog.cancel() }

        builder.show()
    }



}
