package com.example.vkapifeedposts.data.repository

import android.app.Application
import com.example.vkapifeedposts.data.mapper.NewsFeedMapper
import com.example.vkapifeedposts.data.network.ApiFactory
import com.example.vkapifeedposts.data.network.ApiService
import com.example.vkapifeedposts.domain.entity.PostComment
import com.example.vkapifeedposts.domain.entity.FeedPost
import com.example.vkapifeedposts.extensions.mergeWith
import com.example.vkapifeedposts.domain.entity.StatisticItem
import com.example.vkapifeedposts.domain.entity.StatisticType
import com.example.vkapifeedposts.domain.entity.LoginState
import com.example.vkapifeedposts.domain.repository.NewsFeedRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    val storage: VKPreferencesKeyValueStorage,
    val apiService: ApiService,
    val mapper: NewsFeedMapper
) : NewsFeedRepository {


    private val token
        get() = VKAccessToken.restore(storage)

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
        .retry {
            delay(Retry_TimeOut_Millis)
            true
        }
    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private val recommendations: StateFlow<List<FeedPost>> =
        loadedListFlow.mergeWith(refreshListFlow)
            .stateIn(
                scope = coroutineScope,
                started = SharingStarted.Lazily,
                initialValue = feedPosts
            )


    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("token == null")
    }

    private val checkAuthState = MutableSharedFlow<Unit>(replay = 1)

    private val getAuthState = flow {
        checkAuthState.emit(Unit)
        checkAuthState.collect {
            val currentToken = token
            val loggedIn = currentToken != null && currentToken.isValid
            if (loggedIn) emit(LoginState.Auth) else emit(LoginState.NoAuth)
        }
    }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = LoginState.Initial
        )


    override fun getComments(feedPost: FeedPost): StateFlow<List<PostComment>> = flow {
        val dto = apiService.getComments(getAccessToken(), feedPost.comunityId, feedPost.id)
        val postComments = mapper.mapResponseToPostComments(dto)
        emit(postComments)
    }.retry {
        delay(Retry_TimeOut_Millis)
        true
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    override fun getAuthState(): StateFlow<LoginState> = getAuthState

    override fun getRecommendations(): StateFlow<List<FeedPost>> = recommendations

    override suspend fun loadNextData() {
        nextDataNeedeadLoading.emit(Unit)
    }

    override suspend fun checkAuthState() {
        checkAuthState.emit(Unit)
    }

    override suspend fun deleteItem(feedPost: FeedPost) {
        apiService.deleteItem(getAccessToken(), feedPost.comunityId, feedPost.id)
        _feedPosts.remove(feedPost)
        refreshListFlow.emit(feedPosts)
    }

    override suspend fun changeLikeStatus(feedPost: FeedPost) {
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

    companion object {
        private const val Retry_TimeOut_Millis = 3000L
    }
}