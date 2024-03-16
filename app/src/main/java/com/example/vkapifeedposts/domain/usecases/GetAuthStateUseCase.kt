package com.example.vkapifeedposts.domain.usecases

import com.example.vkapifeedposts.domain.entity.FeedPost
import com.example.vkapifeedposts.domain.entity.LoginState
import com.example.vkapifeedposts.domain.entity.PostComment
import com.example.vkapifeedposts.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class GetAuthStateUseCase(private val repository: NewsFeedRepository) {
    operator fun invoke(): StateFlow<LoginState> {
        return repository.getAuthState()
    }
}