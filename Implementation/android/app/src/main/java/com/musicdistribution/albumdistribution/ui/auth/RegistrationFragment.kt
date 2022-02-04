package com.musicdistribution.albumdistribution.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseUser
import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.data.domain.Role
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthDB
import com.musicdistribution.albumdistribution.data.firebase.realtime.FirebaseRealtimeDB
import com.musicdistribution.albumdistribution.databinding.FragmentRegistrationBinding
import com.musicdistribution.albumdistribution.model.firebase.User
import com.musicdistribution.albumdistribution.util.ValidationUtils


class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private lateinit var authActivityViewModel: AuthActivityViewModel
    private var registerCounter: Int = 0
    private var role: Role? = null
    private var firebaseUser: FirebaseUser? = null

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
            ViewModelProvider(requireActivity()).get(AuthActivityViewModel::class.java)

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
        val roleControl =
            view.findViewById<RadioGroup>(R.id.radioGroupRoleRegister).checkedRadioButtonId
        if (ValidationUtils.validateUsername(
                email,
                requireContext()
            ) && ValidationUtils.validatePassword(
                password,
                requireContext()
            ) && ValidationUtils.validateRole(roleControl, requireContext())
        ) {
            role =
                if (view.findViewById<RadioButton>(roleControl).text.equals("Creator")) Role.CREATOR else Role.LISTENER
            registerFirebaseAuth(email, password)
        }
    }

    private fun registerFirebaseAuth(email: String, password: String) {
        FirebaseAuthDB.firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                requireActivity()
            ) { task ->
                if (task.isSuccessful) {
                    firebaseUser = task.result.user
                    registerCounter++
                    registerFirebaseDb(email, role!!)
                    authActivityViewModel.registerApi(email, password, role!!)
                    authActivityViewModel.registerRoom(email, role!!, firebaseUser!!)
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "Registration failed with the message: " + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun registerFirebaseDb(email: String, role: Role) {
        val nameSurname = ValidationUtils.generateFirstLastName(email)
        val user = User(
            name = nameSurname[0],
            surname = nameSurname[1],
            email = email,
            role = role,
            noFollowers = 0L,
            noFollowing = 0L
        )
        FirebaseRealtimeDB.usersReference.child(firebaseUser!!.uid).setValue(user)
            .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                if (task.isSuccessful) {
                    registerCounter++
                    checkRedirect()
                } else {
                    Toast.makeText(
                        activity,
                        "Error: " + task.exception!!.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun validateRegistration() {
        authActivityViewModel.getUsersLiveData()
            .observe(viewLifecycleOwner,
                { t ->
                    if (t != null) {
                        registerCounter++
                        checkRedirect()
                    } else {
                        Toast.makeText(
                            activity,
                            "Error when trying to register, please try again",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })

        authActivityViewModel.getArtistsLiveData()
            .observe(viewLifecycleOwner,
                { t ->
                    if (t != null) {
                        registerCounter++
                        checkRedirect()
                    } else {
                        Toast.makeText(
                            activity,
                            "Error when trying to register, please try again",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
    }

    private fun checkRedirect() {
        if ((registerCounter == 4 && role == Role.CREATOR) ||
            (registerCounter == 3 && role == Role.LISTENER)
        ) {
            Toast.makeText(
                activity,
                "Registration successful",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_RegisterFragment_to_LoginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}