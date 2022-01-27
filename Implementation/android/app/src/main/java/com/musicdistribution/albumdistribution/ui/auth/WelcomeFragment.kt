package com.musicdistribution.albumdistribution.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.facebook.*
import com.facebook.CallbackManager.Factory.create
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthDB
import com.musicdistribution.albumdistribution.databinding.FragmentWelcomeBinding
import com.musicdistribution.albumdistribution.ui.home.HomeActivity


class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!
    private var callbackManager: CallbackManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext())
        AppEventsLogger.activateApp(requireActivity().application)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_WelcomeFragment_to_RegistrationFragment)
        }
        binding.btnLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_WelcomeFragment_to_LoginFragment)
        }

        binding.btnSignFacebook.setOnClickListener {
            facebookSignIn()
        }

        binding.btnSignGoogle.setOnClickListener {
            googleSignIn()
        }

    }

    private fun facebookSignIn() {
        callbackManager = create()

        LoginManager.getInstance().logInWithReadPermissions(this, listOf("openid, email, public_profile"))
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(result: LoginResult?) {
                    if (result != null) {
                        firebaseAuthWithFacebook(result.accessToken)
                    }
                }

                override fun onCancel() {
                    Toast.makeText(
                        activity,
                        "Authentication with Facebook was canceled",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onError(error: FacebookException) {
                    Toast.makeText(
                        activity,
                        "Authentication with Facebook failed with the message: " + error.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun googleSignIn() {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        startActivityForResult(googleSignInClient.signInIntent, FirebaseAuthDB.GS_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FirebaseAuthDB.GS_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            if (task.isSuccessful) {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } else {
                Toast.makeText(
                    activity,
                    "Authentication with Google failed with the message: " + task.exception!!.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        } else if(requestCode == FirebaseAuthDB.FB_SIGN_IN) {
            callbackManager!!.onActivityResult(requestCode, resultCode, data)
        }

    }

    private fun firebaseAuthWithFacebook(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        FirebaseAuthDB.firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful && FirebaseAuthDB.checkLogin()) {
                    navigateOut()
                } else {
                    Toast.makeText(
                        activity,
                        "Authentication with Facebook failed with the message: " + task.exception!!.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuthDB.firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful && FirebaseAuthDB.checkLogin()) {
                    navigateOut()
                } else {
                    Toast.makeText(
                        activity,
                        "Authentication with Google failed with the message: " + task.exception!!.message,
                        Toast.LENGTH_LONG
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