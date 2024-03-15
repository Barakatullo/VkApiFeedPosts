package com.example.vkapifeedposts.presentation.comments

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vkapifeedposts.data.repository.NewsFeedRepository
import com.example.vkapifeedposts.domain.FeedPost
import com.example.vkapifeedposts.presentation.comments.CommentsScreenState
import kotlinx.coroutines.launch

class CommentsViewModel(
    val feedPost: FeedPost,
    application: Application
):ViewModel() {
    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState : LiveData<CommentsScreenState> = _screenState
    private val repository = NewsFeedRepository(application)
init {
    loadComments(feedPost)
}
    fun loadComments(feedPost: FeedPost) {
        viewModelScope.launch {
           val comments = repository.getComments(feedPost)
            _screenState.value = CommentsScreenState.Comments(feedPost,comments)
        }
    }
}