package com.musicdistribution.streamingservice.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.musicdistribution.streamingservice.constant.MessageConstants
import com.musicdistribution.streamingservice.data.api.StreamingServiceApiClient
import com.musicdistribution.streamingservice.data.api.core.AuthServiceApi
import com.musicdistribution.streamingservice.model.enums.EmailDomain
import com.musicdistribution.streamingservice.model.retrofit.UserAuth
import com.musicdistribution.streamingservice.model.retrofit.core.Listener
import com.musicdistribution.streamingservice.model.retrofit.response.ListenerJwt
import com.musicdistribution.streamingservice.util.ValidationUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val app: Application = application
    private val authService: AuthServiceApi = StreamingServiceApiClient.getAuthServiceApi()
    private val loginLiveData: MutableLiveData<ListenerJwt?> = MutableLiveData()

    fun loginApi(email: String, password: String) {
        val username = ValidationUtils.getUsername(email)
        val emailDomain = ValidationUtils.getEmailDomain(email)
        val userAuth = UserAuth(
            username = username,
            emailDomain = emailDomain,
            password = password
        )
        loginUser(userAuth)
    }

    fun registerApi(email: String, password: String) {
        val username = ValidationUtils.getUsername(email)
        val emailDomain = ValidationUtils.getEmailDomain(email)
        val userAuth = UserAuth(
            username = username,
            emailDomain = emailDomain,
            password = password
        )

        registerUser(email, userAuth,
            EmailDomain.values().map { i -> i.toString() } as ArrayList<String>)
    }

    private fun registerUser(email: String, userAuth: UserAuth, emailDomains: ArrayList<String>) {
        if (ValidationUtils.validateEmail(email, emailDomains, app)
            && ValidationUtils.validatePassword(userAuth.password, app)
        ) {
            authService.register(userAuth)
                .enqueue(object : Callback<Listener?> {
                    override fun onResponse(
                        call: Call<Listener?>?,
                        response: Response<Listener?>?
                    ) {
                        Toast.makeText(
                            app,
                            if (response?.body() != null)
                                MessageConstants.SUCCESSFUL_REGISTRATION
                            else MessageConstants.FAILED_REGISTRATION,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onFailure(
                        call: Call<Listener?>?,
                        t: Throwable?
                    ) {
                        Toast.makeText(
                            app,
                            MessageConstants.FAILED_REGISTRATION,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }
    }

    private fun loginUser(userAuth: UserAuth) {
        authService.login(userAuth)
            .enqueue(object : Callback<ListenerJwt?> {
                override fun onResponse(
                    call: Call<ListenerJwt?>?,
                    response: Response<ListenerJwt?>?
                ) {
                    val user = response?.body()
                    if (user != null) {
                        Toast.makeText(
                            app,
                            MessageConstants.SUCCESSFUL_LOGIN,
                            Toast.LENGTH_LONG
                        ).show()
                        loginLiveData.value = user
                    } else {
                        Toast.makeText(
                            app,
                            MessageConstants.FAILED_LOGIN,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<ListenerJwt?>?,
                    t: Throwable?
                ) {
                    Toast.makeText(
                        app,
                        MessageConstants.FAILED_LOGIN,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    fun getLoginLiveData(): MutableLiveData<ListenerJwt?> {
        return loginLiveData
    }
}