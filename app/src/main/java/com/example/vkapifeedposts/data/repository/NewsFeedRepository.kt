package com.example.vkapifeedposts.data.repository

import android.app.Application
import com.example.vkapifeedposts.data.mapper.NewsFeedMapper
import com.example.vkapifeedposts.data.network.ApiFactory
import com.example.vkapifeedposts.domain.PostComment
import com.example.vkapifeedposts.domain.FeedPost
import com.example.vkapifeedposts.extensions.mergeWith
import com.example.vkapifeedposts.domain.StatisticItem
import com.example.vkapifeedposts.domain.StatisticType
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class NewsFeedRepository(application: Application) {
    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)
    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()
    private var nextFrom: String? = null
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val refreshListFlow = MutableSharedFlow<List<FeedPost>>()
    private val nextDataNeedeadLoading = MutableSharedFlow<Unit>(replay = 1)
    private val loadedListFlow = flow {
        nextDataNeedeadLoading.emit(Unit)
        nextDataNeedeadLoading.collect {
            val startFrom = nextFrom

            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }

            val dto = if (startFrom == null) {
                apiService.loadRecomendation(getAccessToken())
            } else {
                apiService.loadRecomendation(getAccessToken(), startFrom)
            }
            nextFrom = dto.response.nextFrom
            val posts = mapper.mapResponseToPosts(dto)
            _feedPosts.addAll(posts)
            emit(feedPosts)
        }

    }
    private val _feedPosts = mutableListOf<FeedPost>()
   private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()





    val recommendations: StateFlow<List<FeedPost>> =loadedListFlow.mergeWith(refreshListFlow)
        .stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = feedPosts
    )

    suspend fun loadNextRecomandations() {
        nextDataNeedeadLoading.emit(Unit)
    }

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("token == null")
    }

    suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(), ownerId = feedPost.comunityId, postId = feedPost.id
            )
        } else {
            apiService.addLike(
                token = getAccessToken(), ownerId = feedPost.comunityId, postId = feedPost.id
            )
        }
        val newLikeCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf {
                it.type == StatisticType.LIKES
            }
            add(StatisticItem(StatisticType.LIKES, newLikeCount))
        }
        val newFeedPost = feedPost.copy(statistics = newStatistics, isLiked = !feedPost.isLiked)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newFeedPost
        refreshListFlow.emit(feedPosts)
    }

    suspend fun deleteItem(feedPost: FeedPost) {
        apiService.deleteItem(getAccessToken(), feedPost.comunityId, feedPost.id)
        _feedPosts.remove(feedPost)
        refreshListFlow.emit(feedPosts)
    }

    suspend fun getComments(feedPost: FeedPost): List<PostComment> {
        val dto = apiService.getComments(getAccessToken(), feedPost.comunityId, feedPost.id)
        val postComments = mapper.mapResponseToPostComments(dto)
        return postComments
    }
}