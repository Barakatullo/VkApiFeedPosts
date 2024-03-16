package com.example.vkapifeedposts.domain

sealed class LoginState {
    object Auth : LoginState()
    object NoAuth : LoginState()
    object Initial : LoginState()
}
