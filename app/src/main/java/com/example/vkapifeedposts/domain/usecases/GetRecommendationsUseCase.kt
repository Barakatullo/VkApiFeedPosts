package com.example.vkapifeedposts.domain.usecases

import com.example.vkapifeedposts.domain.entity.FeedPost
import com.example.vkapifeedposts.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetRecommendationsUseCase@Inject constructor(private val repository: NewsFeedRepository) {
    operator fun invoke(): StateFlow<List<FeedPost>> {
        return repository.getRecommendations()
    }
}