package com.musicdistribution.streamingservice.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.musicdistribution.streamingservice.R
import com.musicdistribution.streamingservice.data.firebase.auth.FirebaseAuthDB
import com.musicdistribution.streamingservice.databinding.FragmentLoginBinding
import com.musicdistribution.streamingservice.ui.home.HomeActivity
import com.musicdistribution.streamingservice.util.ValidationUtils

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBackLogin.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_WelcomeFragment)
        }

        binding.btnLogin.setOnClickListener {
            val email = view.findViewById<EditText>(R.id.inputLoginEmail).text.toString()
            val password = view.findViewById<EditText>(R.id.inputLoginPassword).text.toString()
            if (ValidationUtils.validateUsername(email, requireContext()) && ValidationUtils.validatePassword(
                    password,
                    requireContext()
                )
            ) {
                login(email, password)
            }
        }
    }

    private fun login(email: String, password: String) {
        FirebaseAuthDB.firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful && FirebaseAuthDB.checkLogin()) {
                    navigateOut()
                } else {
                    Toast.makeText(
                        activity, "Login failed with message: " + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun navigateOut() {
        val intent = Intent(activity, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}