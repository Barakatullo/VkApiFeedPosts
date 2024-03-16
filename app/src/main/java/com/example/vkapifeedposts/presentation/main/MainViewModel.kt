package com.example.vkapifeedposts.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkapifeedposts.domain.usecases.CheckAuthStateUseCase
import com.example.vkapifeedposts.domain.usecases.GetAuthStateUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAuthStateUseCase: GetAuthStateUseCase,
    private val checkAuthStateUseCase: CheckAuthStateUseCase
) : ViewModel() {
    val authState = getAuthStateUseCase.invoke()
    fun performAuthResult() {
        viewModelScope.launch {
            checkAuthStateUseCase.invoke()
        }

    }
}