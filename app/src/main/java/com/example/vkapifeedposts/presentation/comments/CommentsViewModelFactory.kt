package com.example.vkapifeedposts.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vkapifeedposts.domain.FeedPost

class CommentsViewModelFactory(
    val feedPost: FeedPost,
    val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(feedPost = feedPost,application) as T
    }
}