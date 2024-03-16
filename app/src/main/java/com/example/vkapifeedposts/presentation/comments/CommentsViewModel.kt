package com.example.vkapifeedposts.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.vkapifeedposts.data.repository.NewsFeedRepositoryImpl
import com.example.vkapifeedposts.domain.entity.FeedPost
import com.example.vkapifeedposts.domain.usecases.GetCommentsUseCase
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CommentsViewModel@Inject constructor(
   private val feedPost: FeedPost,
    private val getCommentsUseCase : GetCommentsUseCase
):ViewModel() {



    val screenState = getCommentsUseCase.invoke(feedPost)
        .map {
            CommentsScreenState.Comments(comments = it, feedPost = feedPost) as CommentsScreenState
        }
}