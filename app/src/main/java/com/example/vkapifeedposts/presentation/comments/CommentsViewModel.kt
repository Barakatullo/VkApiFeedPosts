package com.example.vkapifeedposts.presentation.comments

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkapifeedposts.data.repository.NewsFeedRepository
import com.example.vkapifeedposts.domain.FeedPost
import com.example.vkapifeedposts.presentation.comments.CommentsScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CommentsViewModel(
    val feedPost: FeedPost,
    application: Application
):ViewModel() {

    private val repository = NewsFeedRepository(application)

    val screenState = repository.getComments(feedPost)
        .map {
            CommentsScreenState.Comments(comments = it, feedPost = feedPost) as CommentsScreenState
        }
}