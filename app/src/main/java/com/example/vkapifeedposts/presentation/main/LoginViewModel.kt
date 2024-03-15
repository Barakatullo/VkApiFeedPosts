package com.example.vkapifeedposts.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vkapifeedposts.presentation.main.LoginState
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthenticationResult

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _authState = MutableLiveData<LoginState>(LoginState.Initial)
    val authState : LiveData<LoginState> = _authState
   init {
        val storage = VKPreferencesKeyValueStorage(application)
        val token = VKAccessToken.restore(storage)
        val loggedIn = token != null && token.isValid
        _authState.value = if (loggedIn) LoginState.Auth else LoginState.NoAuth
    }

    fun performAuthResult(result: VKAuthenticationResult) {
        if (result is VKAuthenticationResult.Success) {
            _authState.value = LoginState.Auth
        } else {
            _authState.value = LoginState.NoAuth
        }
    }
}