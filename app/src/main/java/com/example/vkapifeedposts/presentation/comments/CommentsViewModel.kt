package com.example.vkapifeedposts.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.vkapifeedposts.data.repository.NewsFeedRepositoryImpl
import com.example.vkapifeedposts.domain.entity.FeedPost
import com.example.vkapifeedposts.domain.usecases.GetCommentsUseCase
import kotlinx.coroutines.flow.map

class CommentsViewModel(
    val feedPost: FeedPost,
    application: Application
):ViewModel() {

    private val repository = NewsFeedRepositoryImpl(application)

    private val getCommentsUseCase = GetCommentsUseCase(repository)

    val screenState = getCommentsUseCase.invoke(feedPost)
        .map {
            CommentsScreenState.Comments(comments = it, feedPost = feedPost) as CommentsScreenState
        }
}