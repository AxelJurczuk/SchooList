package com.example.android.schoolist.attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.android.schoolist.R
import com.example.android.schoolist.authentication.AuthenticationActivity
import com.example.android.schoolist.authentication.LogInFragment
import com.example.android.schoolist.databinding.ActivityAttendanceBinding
import com.google.firebase.auth.FirebaseAuth

class AttendanceActivity : AppCompatActivity(),OnShowGroupListener {
    private lateinit var binding: ActivityAttendanceBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar= binding.toolbarAttendance
        setSupportActionBar(toolbar)

        if (savedInstanceState==null){
            supportFragmentManager
                .beginTransaction()
                .add(R.id.frame_container_attendance, GroupsFragment())
                .commit()
        }

        auth = FirebaseAuth.getInstance()
        //listener to check if the user is logged in
        authStateListener = FirebaseAuth.AuthStateListener { auth ->
            val firebaseUser = auth.currentUser
            if (firebaseUser == null) {
                val intent = Intent(this, AuthenticationActivity::class.java)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.btn_log_out->{
                auth.signOut()
                Toast.makeText(this, "Logged out", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showGroupInfo(groupName: String) {
        val newListFragment = ListFragment.newInstance(groupName)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_container_attendance, newListFragment)
            .addToBackStack(null)
            .commit()
    }
}