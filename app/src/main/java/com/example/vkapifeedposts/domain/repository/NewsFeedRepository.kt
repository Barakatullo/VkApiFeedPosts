package com.example.vkapifeedposts.domain.repository

import com.example.vkapifeedposts.domain.entity.FeedPost
import com.example.vkapifeedposts.domain.entity.LoginState
import com.example.vkapifeedposts.domain.entity.PostComment
import kotlinx.coroutines.flow.StateFlow
interface NewsFeedRepository {
    fun getAuthState(): StateFlow<LoginState>
    fun getRecommendations(): StateFlow<List<FeedPost>>
    fun getComments(feedPost: FeedPost): StateFlow<List<PostComment>>
    suspend fun checkAuthState()
    suspend fun loadNextData()
    suspend fun changeLikeStatus(feedPost: FeedPost)
    suspend fun deleteItem(feedPost: FeedPost)
}