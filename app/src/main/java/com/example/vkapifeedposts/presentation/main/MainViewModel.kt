package com.example.vkapifeedposts.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkapifeedposts.data.repository.NewsFeedRepositoryImpl
import com.example.vkapifeedposts.domain.usecases.CheckAuthStateUseCase
import com.example.vkapifeedposts.domain.usecases.GetAuthStateUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = NewsFeedRepositoryImpl(application)

    private val getAuthStateUseCase = GetAuthStateUseCase(repository)
    private val checkAuthStateUseCase = CheckAuthStateUseCase(repository)

    val authState = getAuthStateUseCase.invoke()

    fun performAuthResult() {
        viewModelScope.launch {
            checkAuthStateUseCase.invoke()
        }

    }
}