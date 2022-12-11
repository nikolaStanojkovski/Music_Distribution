package com.musicdistribution.streamingservice.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
    }

    private fun registerUser(view: View) {
        val email = view.findViewById<EditText>(R.id.inputRegisterEmail).text.toString()
        val password = view.findViewById<EditText>(R.id.inputRegisterPassword).text.toString()

        authActivityViewModel.registerApi(email, password)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}