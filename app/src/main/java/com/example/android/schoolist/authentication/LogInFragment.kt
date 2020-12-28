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
import com.example.android.schoolist.databinding.FragmentLoginBinding
import com.example.android.schoolist.extensions.remove
import com.example.android.schoolist.extensions.show
import com.google.firebase.auth.FirebaseAuth

class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()
        hideProgressBar()

        binding.btnLogIn.setOnClickListener {
            if (binding.etEmail.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()) {
                showProgressBar()
                auth.signInWithEmailAndPassword(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
                    .addOnCompleteListener {
                        hideProgressBar()
                        if (it.isSuccessful) {
                            Toast.makeText(
                                requireContext(),
                                "Successfully Logged in",
                                Toast.LENGTH_LONG
                            ).show()
                            val intent = Intent(requireContext(), AttendanceActivity::class.java)
                            startActivity(intent)
                            activity?.finish()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Login Failed",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
            } else {
                Toast.makeText(requireContext(), "Complete the fields", Toast.LENGTH_LONG)
                    .show()
            }
        }
        binding.btnSignUp.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_container, SignUpFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
        binding.tvForgotPassword.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_container, ForgotPasswordFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }
        return binding.root
    }
    private fun hideProgressBar(){
        binding.progressBar.remove()
    }
    private fun showProgressBar(){
        binding.progressBar.show()
    }

}