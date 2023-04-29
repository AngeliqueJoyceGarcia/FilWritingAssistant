package com.example.filwritingassistant

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TutorialActivity : AppCompatActivity() {

    //for side menu
    private lateinit var user : FirebaseAuth
    lateinit var drawer: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        val img_letter = findViewById<View>(R.id.tutorial_imageView) as ImageView
        val vid_letter = findViewById <View>(R.id.tutorial_videoView) as VideoView
        val a = findViewById<AppCompatButton>(R.id.btn_ta)
        val b = findViewById<AppCompatButton>(R.id.btn_tb)
        val c = findViewById<AppCompatButton>(R.id.btn_tc)
        val d = findViewById<AppCompatButton>(R.id.btn_td)
        val e = findViewById<AppCompatButton>(R.id.btn_te)
        val f = findViewById<AppCompatButton>(R.id.btn_tf)
        val g = findViewById<AppCompatButton>(R.id.btn_tg)
        val h = findViewById<AppCompatButton>(R.id.btn_th)
        val i = findViewById<AppCompatButton>(R.id.btn_ti)
        val j = findViewById<AppCompatButton>(R.id.btn_tj)
        val k = findViewById<AppCompatButton>(R.id.btn_tk)
        val l = findViewById<AppCompatButton>(R.id.btn_tl)
        val m = findViewById<AppCompatButton>(R.id.btn_tm)
        val n = findViewById<AppCompatButton>(R.id.btn_tn)
        val Ñ = findViewById<AppCompatButton>(R.id.btn_tñ)
        val ng = findViewById<AppCompatButton>(R.id.btn_tng)
        val o = findViewById<AppCompatButton>(R.id.btn_to)
        val p = findViewById<AppCompatButton>(R.id.btn_tp)
        val q = findViewById<AppCompatButton>(R.id.btn_tq)
        val r = findViewById<AppCompatButton>(R.id.btn_tr)
        val s = findViewById<AppCompatButton>(R.id.btn_ts)
        val t = findViewById<AppCompatButton>(R.id.btn_tt)
        val u = findViewById<AppCompatButton>(R.id.btn_tu)
        val v = findViewById<AppCompatButton>(R.id.btn_tv)
        val w = findViewById<AppCompatButton>(R.id.btn_tw)
        val x = findViewById<AppCompatButton>(R.id.btn_tx)
        val y = findViewById<AppCompatButton>(R.id.btn_ty)
        val z = findViewById<AppCompatButton>(R.id.btn_tz)

        //for side menu
        val sidemenu = findViewById<ImageView>(R.id.btnSideMenu)
        val home = findViewById<LinearLayout>(R.id.home)
        val profile = findViewById<LinearLayout>(R.id.profile)
        val aboutUs = findViewById<LinearLayout>(R.id.about)
        val logout = findViewById<LinearLayout>(R.id.logout)
        val database = FirebaseDatabase.getInstance().reference
        val userName = findViewById<TextView>(R.id.tvname)
        val emailAddress = findViewById<TextView>(R.id.tvemail)


        drawer = findViewById(R.id.tutorialparent)

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
                        Toast.makeText(this@TutorialActivity, "Failed to retrieve data from database", Toast.LENGTH_SHORT).show()
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



        /** A **/
        a.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_a
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_aa)
        }

        /** B **/
        b.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_b
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_bb)
        }

        /** C **/
        c.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_c
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_cc)
        }

        /** D **/
        d.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_d
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_dd)
        }

        /** E **/
        e.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_e
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_ee)
        }

        /** F **/
        f.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_f
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_ff)
        }

        /** G **/
        g.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_g
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_gg)
        }
        /** H **/
        h.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_h
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_hh)
        }
        /** I **/
        i.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_i
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_ii)
        }
        /** J **/
        j.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_j
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_jj)
        }
        /** K **/
        k.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_k
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_kk)
        }
        /** L **/
        l.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_l
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_ll)
        }
        /** M **/
        m.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_m
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_mm)
        }
        /** N **/
        n.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_n
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_nn)
        }
        /** Ñ **/
        Ñ.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_enye
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_enyeenye)
        }
        /** Ng **/
        ng.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_ng
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_ngng)
        }
        /** O **/
        o.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_o
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_oo)
        }
        /** P **/
        p.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_p
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_pp)
        }
        /** Q **/
        q.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_q
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_qq)
        }
        /** R **/
        r.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_r
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_rr)
        }
        /** S **/
        s.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_s
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_ss)
        }
        /** T **/
        t.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_t
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_tt)
        }
        /** U **/
        u.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_u
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_uu)
        }
        /** V **/
        v.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_v
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_vv)
        }
        /** W **/
        w.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_w
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_ww)
        }
        /** X **/
        x.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_x
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_xx)
        }
        /** Y **/
        y.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_y
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_yy)
        }
        /** Z **/
        z.setOnClickListener {
            val aPath = "android.resource://" + packageName + "/raw/" + R.raw.t_z
            vid_letter.setVideoURI(Uri.parse(aPath))
            img_letter.setImageResource(R.drawable.t_zz)
        }

        /** Playing the vid base on the set path **/
        vid_letter.setMediaController(MediaController( this))
        vid_letter.start()

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