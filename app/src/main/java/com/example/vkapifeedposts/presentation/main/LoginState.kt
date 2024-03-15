package com.example.vkapifeedposts.presentation.main

sealed class LoginState {
    object Auth : LoginState()
    object NoAuth : LoginState()
    object Initial : LoginState()
}
