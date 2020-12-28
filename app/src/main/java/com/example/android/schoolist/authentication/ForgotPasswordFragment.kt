package com.example.android.schoolist.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.android.schoolist.R
import com.example.android.schoolist.databinding.FragmentForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth


class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentForgotPasswordBinding.inflate(layoutInflater)

        auth= FirebaseAuth.getInstance()

        binding.resetPassword.setOnClickListener {
            resetPassword()
        }
        return binding.root
    }
    private fun resetPassword (){
        val email = binding.etEmail.text.toString()
        if (email.isNotEmpty()) {
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        fragmentManager?.popBackStack()
                        Toast.makeText(context,
                            "New password sent to your email",
                            Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context,
                            "Please enter a valid email",
                            Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            Toast.makeText(context,
                "Field is empty",
                Toast.LENGTH_LONG).show()
        }
    }
}