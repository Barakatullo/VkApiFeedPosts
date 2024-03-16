package com.example.vkapifeedposts.domain.usecases

import com.example.vkapifeedposts.domain.entity.FeedPost
import com.example.vkapifeedposts.domain.entity.PostComment
import com.example.vkapifeedposts.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetCommentsUseCase@Inject constructor(private val repository: NewsFeedRepository) {
    operator fun invoke(feedPost: FeedPost): StateFlow<List<PostComment>> {
        return repository.getComments(feedPost)
    }
}