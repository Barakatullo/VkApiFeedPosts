package com.example.vkapifeedposts.presentation.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkapifeedposts.data.repository.NewsFeedRepository
import com.example.vkapifeedposts.domain.FeedPost
import com.example.vkapifeedposts.extensions.mergeWith
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


class NewsFeedViewModel(application: Application) : AndroidViewModel(application = application) {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("NewsFeedExceptionHandler", "Exception caught by exception handler")
    }

    private val repository = NewsFeedRepository(application)
    private val recomandationFlow = repository.recommendations


    private val loadNextDataEvents = MutableSharedFlow<Unit>()

    private val loadNextDataFlow = flow {
        loadNextDataEvents.collect {
            emit(
                NewsFeedScreenState.Posts(
                    feedposts = recomandationFlow.value,
                    nextDataIsLoading = true
                )
            )
        }
    }

    val screenState = recomandationFlow
        .filter {
            it.isNotEmpty()
        }.map {
            NewsFeedScreenState.Posts(it) as NewsFeedScreenState
        }.onStart {
            emit(NewsFeedScreenState.Loading)
        }.mergeWith(loadNextDataFlow)


    fun chancgeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch {
            repository.changeLikeStatus(feedPost)
        }
    }

    fun loadNextRecommendations() {
        viewModelScope.launch(exceptionHandler) {
            loadNextDataEvents.emit(Unit)
            repository.loadNextRecomandations()
        }


    }

    fun remove(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            repository.deleteItem(feedPost)
        }
    }
}





