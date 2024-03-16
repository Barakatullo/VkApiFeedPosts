package com.example.vkapifeedposts.domain.usecases

import com.example.vkapifeedposts.domain.entity.FeedPost
import com.example.vkapifeedposts.domain.entity.LoginState
import com.example.vkapifeedposts.domain.entity.PostComment
import com.example.vkapifeedposts.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class LoadNextDataUseCase@Inject constructor(private val repository: NewsFeedRepository) {
    suspend operator fun invoke() {
         repository.loadNextData()
    }
}