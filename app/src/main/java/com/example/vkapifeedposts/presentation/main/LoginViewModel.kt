package com.example.vkapifeedposts.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkapifeedposts.data.repository.NewsFeedRepository
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = NewsFeedRepository(application)
    val authState = repository.getAuthState

    fun performAuthResult() {
        viewModelScope.launch {
           repository.checkAuthState()
        }

    }
}