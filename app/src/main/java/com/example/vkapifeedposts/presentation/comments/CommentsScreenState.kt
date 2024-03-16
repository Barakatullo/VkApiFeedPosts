package com.example.vkapifeedposts.presentation.comments

import com.example.vkapifeedposts.domain.entity.PostComment
import com.example.vkapifeedposts.domain.entity.FeedPost
sealed class CommentsScreenState {
    object Initial : CommentsScreenState()
    data class Comments(
        val feedPost: FeedPost,
        val comments : List<PostComment>
    ) : CommentsScreenState()
}
