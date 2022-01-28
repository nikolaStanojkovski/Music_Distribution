package com.musicdistribution.albumdistribution.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthDB
import com.musicdistribution.albumdistribution.databinding.FragmentRegistrationBinding
import com.musicdistribution.albumdistribution.util.ValidationUtils


class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBackRegister.setOnClickListener {
            findNavController().navigate(R.id.action_RegisterFragment_to_WelcomeFragment)
        }

        binding.btnRegister.setOnClickListener {
            val email = view.findViewById<EditText>(R.id.inputRegisterEmail).text.toString()
            val password = view.findViewById<EditText>(R.id.inputRegisterPassword).text.toString()
            if (ValidationUtils.validateUsername(email, requireContext()) && ValidationUtils.validatePassword(
                    password,
                    requireContext()
                )
            ) {
                register(email, password)
            }
        }
    }

    private fun register(email: String, password: String) {
        FirebaseAuthDB.firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                requireActivity()
            ) { task ->
                if (task.isSuccessful && FirebaseAuthDB.checkLogin()) {
                    Toast.makeText(
                        activity,
                        "Registration successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_RegisterFragment_to_LoginFragment)
                } else {
                    Toast.makeText(
                        activity,
                        "Registration failed with the message: " + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}