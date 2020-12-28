package com.example.android.schoolist.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.schoolist.R
import com.example.android.schoolist.attendance.AttendanceActivity
import com.example.android.schoolist.databinding.ActivityAuthenticationBinding
import com.google.firebase.auth.FirebaseAuth


class AuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbarAuthentication
        setSupportActionBar(toolbar)

        auth= FirebaseAuth.getInstance()

        if (savedInstanceState==null){
            supportFragmentManager
                .beginTransaction()
                .add(R.id.frame_container, LogInFragment())
                .commit()
        }

        //listener to check if the user is logged in
        authStateListener = FirebaseAuth.AuthStateListener { auth ->
            val firebaseUser = auth.currentUser
            if (firebaseUser != null) {
                val intent = Intent(this, AttendanceActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(this.authStateListener)

    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(this.authStateListener)
    }
}