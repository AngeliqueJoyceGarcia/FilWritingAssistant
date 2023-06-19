package com.example.filwritingassistant

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Image
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextUtils.substring
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
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


    /** 1 : For Grammar checking **/
    private lateinit var wordset: List<String>
    private lateinit var wordList: List<String>

    private lateinit var expression: List<String>
    private lateinit var exclamation: List<String>
    private lateinit var prefix: List<String>
    private lateinit var interrogative: List<String>
    private lateinit var verb: List<String>
    private lateinit var infinitive: List<String>
    private lateinit var idiomatic: List<String>
    private lateinit var comparative: List<String>
    private lateinit var colloquial: List<String>
    private lateinit var noun: List<String>
    private lateinit var grammar: List<String>
    private lateinit var adjective: List<String>
    private lateinit var preposition: List<String>
    private lateinit var conjunction: List<String>
    private lateinit var interjection: List<String>
    private lateinit var adverb: List<String>
    private lateinit var pronoun: List<String>

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

        /** 1.1 : Continuation of grammar related**/
        wordset = resources.openRawResource(R.raw.wordset).bufferedReader().readLines()
        wordList = wordset.map {it.lowercase()}

        expression = resources.openRawResource(R.raw.expression).bufferedReader().readLines().map { it.lowercase() }
        exclamation = resources.openRawResource(R.raw.exclamation).bufferedReader().readLines().map { it.lowercase() }
        prefix = resources.openRawResource(R.raw.prefix).bufferedReader().readLines().map { it.lowercase() }
        interrogative = resources.openRawResource(R.raw.interrogative).bufferedReader().readLines().map { it.lowercase() }
        verb = resources.openRawResource(R.raw.verb).bufferedReader().readLines().map { it.lowercase() }
        infinitive = resources.openRawResource(R.raw.infinitive).bufferedReader().readLines().map { it.lowercase() }
        idiomatic = resources.openRawResource(R.raw.idiomatic).bufferedReader().readLines().map { it.lowercase() }
        comparative = resources.openRawResource(R.raw.comparative).bufferedReader().readLines().map { it.lowercase() }
        colloquial = resources.openRawResource(R.raw.colloquial).bufferedReader().readLines().map { it.lowercase() }
        noun = resources.openRawResource(R.raw.noun).bufferedReader().readLines().map { it.lowercase() }
        grammar = resources.openRawResource(R.raw.grammar).bufferedReader().readLines().map { it.lowercase() }
        adjective = resources.openRawResource(R.raw.adjective).bufferedReader().readLines().map { it.lowercase() }
        preposition = resources.openRawResource(R.raw.preposition).bufferedReader().readLines().map { it.lowercase() }
        conjunction = resources.openRawResource(R.raw.conjunction).bufferedReader().readLines().map { it.lowercase() }
        interjection = resources.openRawResource(R.raw.interjection).bufferedReader().readLines().map { it.lowercase() }
        adverb = resources.openRawResource(R.raw.adverb).bufferedReader().readLines().map { it.lowercase() }
        pronoun = resources.openRawResource(R.raw.pronoun).bufferedReader().readLines().map { it.lowercase() }

        // List of special cases
        val titles = listOf("mr.", "ms.", "mrs.", "dr.", "dra.", "atty.",
                                       "mr", "ms", "mrs", "dr", "dra", "atty",
                                       "g", "gng", "bb", "g.", "gng.", "bb.")
        val special_c = listOf("daw", "din", "dito", "diyan", "doon")
        val special_v = listOf("raw", "rin", "rito", "riyan", "roon")
        val vowels = listOf("a", "e", "i", "o", "u", "w", "y")

        drawer = findViewById(R.id.parenttexteditor)

        //for saving in cloud
        val savetoCloud = findViewById<ImageView>(R.id.btntocloud)
        texteditor = findViewById(R.id.et_text_editor) // First initialization of text editor

        //for download in the device
        val downloadToDevice = findViewById<ImageView>(R.id.btntodevice)

        //for upload in the text editor
        val upload = findViewById<ImageView>(R.id.btnupload)

        //setting the recognized text to text editor

        val text = intent.getStringExtra("text")
        val text_view = intent.getStringExtra("fileContent")
        texteditor.setText(text ?: text_view)


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
            val data = texteditor.text.toString()
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
        // suggestion list
        val suggestions = findViewById<ListView>(R.id.lvsuggestions)

        // Create an empty list to hold suggestions
        val suggestionList = mutableListOf<String>()

        // Create an adapter for the suggestions list view
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, suggestionList)
        suggestions.adapter = adapter

        // Add a text change listener to the text editor
        texteditor.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {

                // Clear the suggestion list
                suggestionList.clear()

                // Step 1: Get the text from the editor and split it into sentences
                val text = s.toString()
                val sentences = text.split(Regex("(?<=\\.)\\s+"))

                // For handling case 2
                var prevToken : Any? = null

                for (sentence in sentences) {

                    // Step 1.2: split sentence into tokens
                    val tokens = sentence.split(Regex("\\s+"))

                    // Step 2.1: Check every sentence if the first word starts with a capital letter
                    // and is not part of title
                    if (tokens.first().matches("\\p{Lu}.*".toRegex()) &&
                        !titles.contains(tokens.first())) {

                        // Step 3: Check every word
                        for (i in tokens.indices) {
                            val token = tokens[i]

                            // Case 1: Word is in title list
                            if (titles.contains(token.lowercase())){

                                // Case 1a: First letter is not capitalized
                                if (!Character.isUpperCase(token[0])) {
                                    suggestionList.add("Please capitalize the first letter of the title: $token")
                                }

                                // Case 1b: Title does not have dot
                                if (!token.contains(".")) {
                                    suggestionList.add("Add a period to this title: $token. ")
                                }

                                // Case 1c: Next word starts with lowercase
                                if (i < tokens.lastIndex && tokens[i + 1].matches("(\\s)*\\p{Ll}.*".toRegex())) {
                                    var nextToken = tokens[i + 1][0]
                                    suggestionList.add("Please capitalize the first letter of : $nextToken")
                                }
                            }
                            // Case 2: Word is in special_c list CONSONANT
                            else if (special_v.contains(token.lowercase())){

                                // Case 2a: if the last letter of the last token is not in vowels list.
                                if (prevToken != null && prevToken is String && prevToken.last().toString().lowercase() !in vowels) {
                                    suggestionList.add("Instead of 'R' use 'D' in the first letter: $token")
                                }
                            }
                            // Case 3: Word is in special_c list CONSONANT
                            else if (special_c.contains(token.lowercase())){

                                // Case 3a: if the last letter of the last token is in vowels list.
                                if (prevToken != null && prevToken is String && prevToken.last().toString().lowercase() in vowels) {
                                    suggestionList.add("Instead of 'D' use 'R' in the first letter: $token")
                                }
                            }
                            // Setting up previous token
                            prevToken = token
                        }

                        // Case 4: Suggesting closest word from word list
                        for (token in tokens){
                            var token = token.lowercase()

                            if (token.endsWith(".") && token !in titles) {
                                // remove the period and check if the word is in the wordList

                                val word = token.substring(0, token.length - 1)
                                if (!wordList.contains(word.lowercase())) {

                                    // find the closest word in the wordList using Levenshtein distance
                                    val closestWord = wordList.minByOrNull { levenshteinDistance(word.lowercase(), it) }
                                    val suggestion = if (closestWord != null) "Did you mean: $closestWord" else "Word not found"
                                    suggestionList.add("$token | $suggestion")
                                }
                            } else if (!titles.contains(token.lowercase()) && !wordList.contains(token.lowercase())) {

                                // find the closest word in the wordList using Levenshtein distance
                                val closestWord = wordList.minByOrNull { levenshteinDistance(token.lowercase(), it) }
                                val suggestion = if (closestWord != null) "Did you mean: $closestWord" else "Word not found"
                                suggestionList.add("$token | $suggestion")
                            } else if (wordList.contains(token.lowercase()) || token.isBlank()){
                                // word is in the wordlist
                                continue
                            }
                        }// end of case 4

                    } else {// Step 2.2: not part of titles and the first letter is lowercase
                        if (tokens.first().isNotEmpty()) {
                            suggestionList.add("Please capitalize the first letter of: ${tokens.first()}")
                        }
                    }

                    // Update the adapter with the new suggestion list
                    adapter.notifyDataSetChanged()
                }// end of for (sentence in sentences)

                // Step 4: Rearrange if possible
                for (statement in sentences){
                    // removing period
                    statement?.let {
                        val statementWithoutPeriod = if (it.endsWith('.')) {
                            it.removeSuffix(".")
                        } else {
                            it
                        }
                        // getting the pattern and trying to re-arrange
                        val tokenArray = tokenize(statementWithoutPeriod.lowercase())
                        val getPattern = getPattern(tokenArray)
                        val closestPattern = findClosestPattern(getPattern)
                        val hint = rearrange(closestPattern, getPattern, tokenArray.toString())

                        // if re-arrange is possible
                        if (hint.isNotEmpty()) {
                            if (hint.toString() != tokenArray.toString()){
                                suggestionList.add("Did you mean: " + hint.joinToString(separator = " "))
                            }

                        }

                        adapter.notifyDataSetChanged()
                    }

                } // end of step 4

            }// end of aftertextchanged

            })// end of texteditor.textchangedlistener

    } // end of on create

    fun levenshteinDistance(s: String, t: String): Int {
        val m = s.length
        val n = t.length
        val d = Array(m + 1) { IntArray(n + 1) }

        for (i in 0..m) {
            d[i][0] = i
        }

        for (j in 0..n) {
            d[0][j] = j
        }

        for (j in 1..n) {
            for (i in 1..m) {
                if (s[i - 1] == t[j - 1]) {
                    d[i][j] = d[i - 1][j - 1]
                } else {
                    d[i][j] = 1 + minOf(
                        d[i - 1][j],
                        d[i][j - 1],
                        d[i - 1][j - 1]
                    )
                }
            }
        }

        return d[m][n]
    }

    // for rearrangement side in NLP
    fun findClosestPattern(queryPattern: List<String>): List<String>? {
        val posPattern = listOf(
            // 3 token
            listOf("adjective", "pronoun", "verb"), // E2
            listOf("adjective", "grammar", "noun"), // E4, E5
            listOf("grammar", "adverb", "noun"), // E6
            listOf("grammar", "noun", "pronoun"), // E7
            listOf("pronoun", "grammar", "adjective"), // E8
            listOf("adverb", "pronoun", "adjective"), // E9, E10
            listOf("adverb", "pronoun", "verb"), // E3
            listOf("verb", "grammar", "adjective"), // E11
            listOf("verb", "grammar", "noun"), // E12
            listOf("noun", "pronoun", "adverb"), // E1
            // 4 token
            listOf("verb", "pronoun", "preposition", "noun"), // E1
            listOf("verb", "pronoun", "preposition", "noun"), // E2, E3, E4
            listOf("verb", "preposition", "adjective", "adverb"), // E5
            listOf("verb", "pronoun", "grammar", "adjective"), // E9
            listOf("verb", "grammar", "noun", "pronoun"), // E10, E11
            listOf("verb", "grammar", "noun", "adverb"), // E12
            listOf("adjective", "grammar", "noun", "adverb"), // E6, E7, E8
            // 5 token
            listOf("pronoun", "grammar", "verb", "preposition", "noun"), //E1. E2
            listOf("verb", "preposition", "adjective", "grammar", "noun"), //E3
            listOf("adjective", "adverb", "verb", "grammar", "noun"), //E4
            listOf("adjective", "pronoun", "grammar", "verb", "adverb"), //E5
            listOf("adjective", "pronoun", "grammar", "noun", "adverb"), //E7
            listOf("noun", "pronoun", "verb", "adjective", "adverb"), //E6

        )
        var closestPattern: List<String>? = null
        var maxMatches = 0

        val filteredPatterns = posPattern.filter { pattern -> pattern.size == queryPattern.size }

        for (pattern in filteredPatterns) {
            var matches = 0
            val sortedQuery = queryPattern.sorted()
            val sortedPattern = pattern.sorted()
            for ((i, part) in sortedQuery.withIndex()) {
                if (part == sortedPattern[i]) {
                    matches++
                }
            }
            if (matches > maxMatches) {
                maxMatches = matches
                closestPattern = pattern
            }
        }

        return closestPattern
    }
    fun rearrange(patternToUse: List<String>?, queryPattern: List<String>?, text: String?): List<String> {
        // Check for null values
        if (patternToUse?.contains("unknown") == true || queryPattern?.contains("unknown") == true || text == null) {
            return emptyList()
        }

        val tokenArray = tokenize(text)
        // Check length of query_pattern and token_array
        if (queryPattern != null) {
            if (queryPattern.size != tokenArray.size) {
                return emptyList()
            }
        }

        val wordDict = queryPattern?.zip(tokenArray)?.toMap()
        if (patternToUse != null) {
            if (wordDict != null) {
                return patternToUse.map { wordDict.getOrDefault(it, "") }
            }
        }

        return emptyList()
    }
    fun tokenize(sentence: String): List<String> {
        val regex = Regex("[^A-Za-z0-9ñÑ ]")
        val sanitizedSentence = sentence.replace(regex, "")
        return sanitizedSentence.split(" ")
    }
    fun getPattern(tokenArray: List<String>): List<String> {

        val identifiedTypes = mutableListOf<String>()

        val wordLists = mapOf("expression" to resources.openRawResource(R.raw.expression).bufferedReader().readLines().map { it.lowercase() },
            "exclamation" to resources.openRawResource(R.raw.exclamation).bufferedReader().readLines().map { it.lowercase() },
            "adverb" to resources.openRawResource(R.raw.adverb).bufferedReader().readLines().map { it.lowercase() },
            "prefix" to resources.openRawResource(R.raw.prefix).bufferedReader().readLines().map { it.lowercase() },
            "interrogative" to resources.openRawResource(R.raw.interrogative).bufferedReader().readLines().map { it.lowercase() },
            "verb" to resources.openRawResource(R.raw.verb).bufferedReader().readLines().map { it.lowercase() },
            "infinitive" to resources.openRawResource(R.raw.infinitive).bufferedReader().readLines().map { it.lowercase() },
            "idiomatic" to resources.openRawResource(R.raw.idiomatic).bufferedReader().readLines().map { it.lowercase() },
            "comparative" to resources.openRawResource(R.raw.comparative).bufferedReader().readLines().map { it.lowercase() },
            "colloquial" to resources.openRawResource(R.raw.colloquial).bufferedReader().readLines().map { it.lowercase() },
            "noun" to resources.openRawResource(R.raw.noun).bufferedReader().readLines().map { it.lowercase() },
            "grammar" to resources.openRawResource(R.raw.grammar).bufferedReader().readLines().map { it.lowercase() },
            "adjective" to resources.openRawResource(R.raw.adjective).bufferedReader().readLines().map { it.lowercase() },
            "preposition" to resources.openRawResource(R.raw.preposition).bufferedReader().readLines().map { it.lowercase() },
            "conjunction" to resources.openRawResource(R.raw.conjunction).bufferedReader().readLines().map { it.lowercase() },
            "interjection" to resources.openRawResource(R.raw.interjection).bufferedReader().readLines().map { it.lowercase() },
            "pronoun" to resources.openRawResource(R.raw.pronoun).bufferedReader().readLines().map { it.lowercase() })

        for (token in tokenArray) {
            var found = false
            for ((wordListName, wordList) in wordLists) {
                if (token in wordList) {
                    identifiedTypes.add(wordListName)
                    found = true
                    break
                }
            }
            if (!found) {
                identifiedTypes.add("Unknown")
            }
        }

        return identifiedTypes
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
                texteditor.setText(text)
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
