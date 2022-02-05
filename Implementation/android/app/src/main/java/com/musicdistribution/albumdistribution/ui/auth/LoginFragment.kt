package com.musicdistribution.albumdistribution.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseUser
import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.data.domain.Role
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthDB
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthUser
import com.musicdistribution.albumdistribution.data.firebase.realtime.FirebaseRealtimeDB
import com.musicdistribution.albumdistribution.databinding.FragmentLoginBinding
import com.musicdistribution.albumdistribution.ui.home.HomeActivity
import com.musicdistribution.albumdistribution.util.ValidationUtils

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var authActivityViewModel: AuthActivityViewModel
    private var loginCounter: Int = 0
    private lateinit var firebaseUser: FirebaseUser

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
            ViewModelProvider(requireActivity())[AuthActivityViewModel::class.java]

        binding.btnBackLogin.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_WelcomeFragment)
        }

        binding.btnLogin.setOnClickListener {
            val email = view.findViewById<EditText>(R.id.inputLoginEmail).text.toString()
            val password = view.findViewById<EditText>(R.id.inputLoginPassword).text.toString()
            if (ValidationUtils.validateUsername(
                    email,
                    requireContext()
                ) && ValidationUtils.validatePassword(
                    password,
                    requireContext()
                )
            ) {
                loginFirebase(email, password)
            }
        }

        validateLogin()
    }

    private fun loginFirebase(email: String, password: String) {
        FirebaseAuthDB.firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    loginCounter++
                    firebaseUser = task.result.user!!
                    loginFirebaseDb(email)
                } else {
                    Toast.makeText(
                        activity, "Login failed with message: " + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun loginFirebaseDb(email: String) {
        FirebaseRealtimeDB.usersReference.child("/${firebaseUser.uid}").get()
            .addOnSuccessListener { user ->
                if (user.exists()) {
                    loginCounter++
                    FirebaseAuthUser.updateUser(user)
                    checkRedirect()
                } else {
                    Toast.makeText(
                        activity,
                        "There is no user with such username",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun validateLogin() {
        authActivityViewModel.getUsersLiveData()
            .observe(viewLifecycleOwner,
                { t ->
                    if (t != null) {
                        loginCounter++
                        checkRedirect()
                    }
                })
    }

    private fun checkRedirect() {
        if (loginCounter == 2) {
            Toast.makeText(
                activity,
                "Login successful",
                Toast.LENGTH_SHORT
            ).show()
            navigateOut()
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