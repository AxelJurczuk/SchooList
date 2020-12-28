package com.example.android.schoolist.authentication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.schoolist.R
import com.example.android.schoolist.attendance.AttendanceActivity
import com.example.android.schoolist.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth


class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSignUpBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()
        binding.btnSignUp.setOnClickListener {

            if (binding.etEmail.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "Successfully Registered",
                                Toast.LENGTH_LONG
                            ).show()
                            val intent = Intent(requireContext(), AttendanceActivity::class.java)
                            startActivity(intent)
                            activity?.finish()
                        } else {
                            Toast.makeText(requireContext(),
                                "Registration Failed",
                                Toast.LENGTH_LONG)
                                .show()
                        }
                    }
            } else {
                Toast.makeText(requireContext(), "Complete the fields", Toast.LENGTH_LONG)
                    .show()
            }
        }

        return binding.root
    }

}