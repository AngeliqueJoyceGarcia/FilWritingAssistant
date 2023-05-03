package com.example.filwritingassistant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class LetterPickerSimuActivity : AppCompatActivity() {

    lateinit var letterCapitalization: String
    lateinit var classIndex: String

    //for side menu
    private lateinit var user : FirebaseAuth
    lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letter_picker_simu)

        val A = findViewById<TextView>(R.id.btnBigA)
        val a = findViewById<TextView>(R.id.btnSmallA)
        val B = findViewById<TextView>(R.id.btnBigB)
        val b = findViewById<TextView>(R.id.btnSmallB)
        val C = findViewById<TextView>(R.id.btnBigC)
        val c = findViewById<TextView>(R.id.btnSmallC)
        val D = findViewById<TextView>(R.id.btnBigD)
        val d = findViewById<TextView>(R.id.btnSmallD)
        val E = findViewById<TextView>(R.id.btnBigE)
        val e = findViewById<TextView>(R.id.btnSmallE)
        val F = findViewById<TextView>(R.id.btnBigF)
        val f = findViewById<TextView>(R.id.btnSmallF)
        val G = findViewById<TextView>(R.id.btnBigG)
        val g= findViewById<TextView>(R.id.btnSmallG)
        val H = findViewById<TextView>(R.id.btnBigH)
        val h = findViewById<TextView>(R.id.btnSmallH)
        val I = findViewById<TextView>(R.id.btnBigI)
        val i = findViewById<TextView>(R.id.btnSmallI)
        val J = findViewById<TextView>(R.id.btnBigJ)
        val j = findViewById<TextView>(R.id.btnSmallJ)
        val K = findViewById<TextView>(R.id.btnBigK)
        val k = findViewById<TextView>(R.id.btnSmallK)
        val L = findViewById<TextView>(R.id.btnBigL)
        val l = findViewById<TextView>(R.id.btnSmallL)
        val M = findViewById<TextView>(R.id.btnBigM)
        val m = findViewById<TextView>(R.id.btnSmallM)
        val N = findViewById<TextView>(R.id.btnBigN)
        val n = findViewById<TextView>(R.id.btnSmallN)
        val Ñ = findViewById<TextView>(R.id.btnBigÑ)
        val ñ = findViewById<TextView>(R.id.btnSmallÑ)
        val NG = findViewById<TextView>(R.id.btnBigNG)
        val ng = findViewById<TextView>(R.id.btnSmallNG)
        val O = findViewById<TextView>(R.id.btnBigO)
        val o = findViewById<TextView>(R.id.btnSmallO)
        val P = findViewById<TextView>(R.id.btnBigP)
        val p = findViewById<TextView>(R.id.btnSmallP)
        val Q = findViewById<TextView>(R.id.btnBigQ)
        val q = findViewById<TextView>(R.id.btnSmallQ)
        val RR = findViewById<TextView>(R.id.btnBigR)
        val rr = findViewById<TextView>(R.id.btnSmallR)
        val S = findViewById<TextView>(R.id.btnBigS)
        val s = findViewById<TextView>(R.id.btnSmallS)
        val T = findViewById<TextView>(R.id.btnBigT)
        val t = findViewById<TextView>(R.id.btnSmallT)
        val U = findViewById<TextView>(R.id.btnBigU)
        val u = findViewById<TextView>(R.id.btnSmallU)
        val V = findViewById<TextView>(R.id.btnBigV)
        val v = findViewById<TextView>(R.id.btnSmallV)
        val W = findViewById<TextView>(R.id.btnBigW)
        val w = findViewById<TextView>(R.id.btnSmallW)
        val X = findViewById<TextView>(R.id.btnBigX)
        val x = findViewById<TextView>(R.id.btnSmallX)
        val Y = findViewById<TextView>(R.id.btnBigY)
        val y = findViewById<TextView>(R.id.btnSmallY)
        val Z = findViewById<TextView>(R.id.btnBigZ)
        val z = findViewById<TextView>(R.id.btnSmallZ)

        //for side menu
        val sidemenu = findViewById<ImageView>(R.id.btnSideMenu5)
        val home = findViewById<LinearLayout>(R.id.home)
        val profile = findViewById<LinearLayout>(R.id.profile)
        val aboutUs = findViewById<LinearLayout>(R.id.about)
        val logout = findViewById<LinearLayout>(R.id.logout)
        val database = FirebaseDatabase.getInstance().reference
        val userName = findViewById<TextView>(R.id.tvname)
        val emailAddress = findViewById<TextView>(R.id.tvemail)


        drawer = findViewById(R.id.parentPicker)

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
                        Toast.makeText(this@LetterPickerSimuActivity, "Failed to retrieve data from database", Toast.LENGTH_SHORT).show()
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


        A.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "0"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        a.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "0"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        B.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "1"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        b.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "1"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        C.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "2"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        c.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "2"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        D.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "3"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        d.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "3"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        E.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "4"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        e.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "4"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        F.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "5"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        f.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "5"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        G.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "6"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        g.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "6"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        H.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "7"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        h.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "7"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        I.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "8"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        i.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "8"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        J.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "9"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        j.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "9"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        K.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "10"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        k.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "10"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        L.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "11"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        l.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "11"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        M.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "12"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        m.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "12"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        N.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "13"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        n.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "13"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        Ñ.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "14"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        ñ.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "14"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        NG.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "15"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        ng.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "15"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        O.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "16"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        o.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "16"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        P.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "17"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        p.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "17"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        Q.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "18"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        q.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "18"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        RR.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "19"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        rr.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "19"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        S.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "20"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        s.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "20"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        T.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "21"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        t.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "21"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        U.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "22"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        u.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "22"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        V.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "23"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        v.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "23"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        W.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "24"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        w.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "24"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        X.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "25"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        x.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "25"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        Y.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "26"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        y.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "26"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        Z.setOnClickListener {
            letterCapitalization = "BIG"
            classIndex = "27"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
        }

        z.setOnClickListener {
            letterCapitalization = "SMALL"
            classIndex = "27"

            val intent = Intent(this, SimulationActivity::class.java)
            intent.putExtra("m1", letterCapitalization)
            intent.putExtra("m2", classIndex)
            startActivity(intent)
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