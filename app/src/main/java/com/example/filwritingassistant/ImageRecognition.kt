package com.example.filwritingassistant

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.File

class ImageRecognition : AppCompatActivity() {

    private val REQUEST_IMAGE_CAPTURE = 1 // unique request code
    private val REQUEST_CAMERA_PERMISSION = 2 // unique request code
    lateinit var output: TextView

    //for side menu
    private lateinit var user : FirebaseAuth
    lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_recognition)

        val capture = findViewById<ImageView>(R.id.btncapture)
        val proceed = findViewById<ImageView>(R.id.btncontinue)
        output = findViewById(R.id.tvPredictedtext)

        //for side menu
        val sidemenu = findViewById<ImageView>(R.id.btnSideMenu)
        val home = findViewById<LinearLayout>(R.id.home)
        val profile = findViewById<LinearLayout>(R.id.profile)
        val aboutUs = findViewById<LinearLayout>(R.id.about)
        val logout = findViewById<LinearLayout>(R.id.logout)
        val database = FirebaseDatabase.getInstance().reference
        val userName = findViewById<TextView>(R.id.tvname)
        val emailAddress = findViewById<TextView>(R.id.tvemail)


        drawer = findViewById(R.id.imgrecogparent)

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
                        Toast.makeText(this@ImageRecognition, "Failed to retrieve data from database", Toast.LENGTH_SHORT).show()
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


        capture.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            }
        }

        val capturedImage = findViewById<ImageView>(R.id.ivCapturedImage)

        proceed.setOnClickListener {
            val defaultImageResource = R.drawable.defaultpictureimagerecog

            if (capturedImage.drawable.constantState == resources.getDrawable(defaultImageResource).constantState) {
                Toast.makeText(this, "No captured photo", Toast.LENGTH_SHORT).show()
            } else {
                val text = output.text.toString() // get text from output TextView
                val intent = Intent(this, TextEditor::class.java)
                intent.putExtra("text", text) // pass text to TextEditorActivity
                startActivity(intent) // start TextEditorActivity
            }
        }



        sidemenu.setOnClickListener {
            openDrawer(drawer)
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



    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            val fileName = "myImage.jpg"
            val storageDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath + "/.hiddenFolder/"
            val file = File(storageDir)
            if (!file.exists()) {
                file.mkdirs()
            }
            val imageFile = File(file, fileName)
            val imageUri = FileProvider.getUriForFile(this, "com.example.filwritingassistant.fileprovider", imageFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // Load the saved image from file
            val fileName = "myImage.jpg"
            val storageDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath + "/.hiddenFolder/"
            val file = File(storageDir, fileName)
            val imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, Uri.fromFile(file))

            val textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

            // Process the newly captured image
            val image = InputImage.fromBitmap(imageBitmap, 0)

            textRecognizer.process(image)
                .addOnSuccessListener { result ->
                    // Get recognized text and set it to text view
                    val recognizedText = result.text
                    output.text = recognizedText
                }
                .addOnFailureListener { e ->
                    // Log error message
                    Log.e("TextRecognition", "Text recognition failed", e)
                }

            if (imageBitmap != null) {
                val capturedImage = findViewById<ImageView>(R.id.ivCapturedImage)
                capturedImage.setImageBitmap(imageBitmap)
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            dispatchTakePictureIntent()
        }
    }
}
