package com.example.vkapifeedposts.presentation.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkapifeedposts.domain.entity.FeedPost
import com.example.vkapifeedposts.domain.usecases.ChangeLikeStatusUseCase
import com.example.vkapifeedposts.domain.usecases.DeleteItemUseCase
import com.example.vkapifeedposts.domain.usecases.GetRecommendationsUseCase
import com.example.vkapifeedposts.domain.usecases.LoadNextDataUseCase
import com.example.vkapifeedposts.extensions.mergeWith
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


class NewsFeedViewModel@Inject constructor(
    private val getRecommendationsUseCase : GetRecommendationsUseCase,
            private val loadNextDataUseCase : LoadNextDataUseCase,
            private val changeLikeStatusUseCase : ChangeLikeStatusUseCase,
            private val deleteItemUseCase : DeleteItemUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("NewsFeedExceptionHandler", "Exception caught by exception handler")
    }



    private val recomandationFlow = getRecommendationsUseCase.invoke()


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
            changeLikeStatusUseCase.invoke(feedPost)
        }
    }

    fun loadNextRecommendations() {
        viewModelScope.launch(exceptionHandler) {
            loadNextDataEvents.emit(Unit)
            loadNextDataUseCase.invoke()
        }
    }

    fun remove(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            deleteItemUseCase.invoke(feedPost)
        }
    }
}





