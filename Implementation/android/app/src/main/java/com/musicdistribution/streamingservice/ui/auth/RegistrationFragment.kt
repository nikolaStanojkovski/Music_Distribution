package com.musicdistribution.streamingservice.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.musicdistribution.streamingservice.util.ValidationUtils
import streamingservice.R
import streamingservice.databinding.FragmentRegistrationBinding


class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private lateinit var authActivityViewModel: AuthActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authActivityViewModel =
            ViewModelProvider(this)[AuthActivityViewModel::class.java]

        binding.btnBackRegister.setOnClickListener {
            findNavController().navigate(R.id.action_RegisterFragment_to_WelcomeFragment)
        }

        binding.btnRegister.setOnClickListener {
            registerUser(view)
        }

        validateRegistration()
    }

    private fun registerUser(view: View) {
        val email = view.findViewById<EditText>(R.id.inputRegisterEmail).text.toString()
        val password = view.findViewById<EditText>(R.id.inputRegisterPassword).text.toString()
        if (ValidationUtils.validateUsername(
                email,
                requireContext()
            ) && ValidationUtils.validatePassword(
                password,
                requireContext()
            )
        ) {
//            register(email, password)
        }
    }

//    private fun register(email: String, password: String) {
//        val nameSurname = ValidationUtils.generateFirstLastName(email)
//        val user = User(
//            name = nameSurname[0],
//            surname = nameSurname[1],
//            email = email,
//            noFollowers = 0L,
//            noFollowing = 0L
//        )
//        FirebaseRealtimeDB.usersReference.child(firebaseUser!!.uid).setValue(user)
//            .addOnCompleteListener(OnCompleteListener<Void?> { task ->
//                if (task.isSuccessful) {
//                    if (role == Role.CREATOR) {
//                        authActivityViewModel.registerApi(email, password)
//                    } else {
//                        redirect()
//                    }
//                } else {
//                    Toast.makeText(
//                        activity,
//                        "Error: " + task.exception!!.message,
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            })
//    }

    private fun validateRegistration() {
        authActivityViewModel.getArtistsLiveData()
            .observe(viewLifecycleOwner,
                { artist ->
                    if (artist != null) {
                        redirect()
                    } else {
                        Toast.makeText(
                            activity,
                            "Error when trying to register, please try again",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
    }

    private fun redirect() {
        Toast.makeText(
            activity,
            "Registration successful",
            Toast.LENGTH_SHORT
        ).show()
        findNavController().navigate(R.id.action_RegisterFragment_to_LoginFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}