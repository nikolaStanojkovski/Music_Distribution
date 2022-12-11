package com.musicdistribution.streamingservice.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.musicdistribution.streamingservice.constants.ApiConstants
import com.musicdistribution.streamingservice.constants.EntityConstants
import com.musicdistribution.streamingservice.data.SessionService
import com.musicdistribution.streamingservice.model.retrofit.ListenerJwt
import com.musicdistribution.streamingservice.ui.HomeActivity
import streamingservice.R
import streamingservice.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var authActivityViewModel: AuthActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authActivityViewModel =
            ViewModelProvider(this)[AuthActivityViewModel::class.java]

        binding.btnBackLogin.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_WelcomeFragment)
        }

        binding.btnLogin.setOnClickListener {
            val email = view.findViewById<EditText>(R.id.inputLoginEmail).text.toString()
            val password = view.findViewById<EditText>(R.id.inputLoginPassword).text.toString()

            authActivityViewModel.loginApi(email, password)
        }

        validateLogin()
    }

    private fun validateLogin() {
        authActivityViewModel.getLoginLiveData()
            .observe(viewLifecycleOwner,
                { listener ->
                    if (listener != null && listener.jwtToken.isNotEmpty()) {
                        saveUserInfo(listener)
                        navigateOut()
                    }
                })
    }

    private fun saveUserInfo(listener: ListenerJwt) {
        if (listener.listenerResponse.id.isNotEmpty() && listener.listenerResponse.email.isNotEmpty()) {
            SessionService.save(ApiConstants.ACCESS_TOKEN, listener.jwtToken)
            SessionService.save(EntityConstants.USER_ID, listener.listenerResponse.id)
            SessionService.save(EntityConstants.USER_EMAIL, listener.listenerResponse.email)
        }
    }

    private fun navigateOut() {
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}