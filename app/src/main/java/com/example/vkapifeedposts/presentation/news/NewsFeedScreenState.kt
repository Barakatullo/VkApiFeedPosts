package com.example.vkapifeedposts.presentation.news

import com.example.vkapifeedposts.domain.FeedPost

sealed class NewsFeedScreenState {
    object Initial : NewsFeedScreenState()
    object Loading : NewsFeedScreenState()
    data class Posts(
        val feedposts : List<FeedPost>,
        val nextDataIsLoading: Boolean = false
    ) : NewsFeedScreenState()
}
