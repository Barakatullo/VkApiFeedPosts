package com.example.vkapifeedposts.presentation.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkapifeedposts.data.repository.NewsFeedRepositoryImpl
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


class NewsFeedViewModel(application: Application) : AndroidViewModel(application = application) {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("NewsFeedExceptionHandler", "Exception caught by exception handler")
    }


    private val repository = NewsFeedRepositoryImpl(application)

    private val getRecommendationsUseCase = GetRecommendationsUseCase(repository)
    private val loadNextDataUseCase = LoadNextDataUseCase(repository)
    private val changeLikeStatusUseCase = ChangeLikeStatusUseCase(repository)
    private val deleteItemUseCase = DeleteItemUseCase(repository)

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





