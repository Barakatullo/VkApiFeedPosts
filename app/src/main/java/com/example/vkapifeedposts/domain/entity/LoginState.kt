package com.example.vkapifeedposts.domain.entity

sealed class LoginState {
    object Auth : LoginState()
    object NoAuth : LoginState()
    object Initial : LoginState()
}
