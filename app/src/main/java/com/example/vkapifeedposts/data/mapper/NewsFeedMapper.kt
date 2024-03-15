package com.example.vkapifeedposts.data.mapper

import com.example.vkapifeedposts.data.module.comments.CommentsResponseDto
import com.example.vkapifeedposts.domain.PostComment
import com.example.vkapifeedposts.data.module.NewsFeedResponseDto
import com.example.vkapifeedposts.domain.FeedPost
import com.example.vkapifeedposts.domain.StatisticItem
import com.example.vkapifeedposts.domain.StatisticType
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.absoluteValue

class NewsFeedMapper {
    fun mapResponseToPosts(responseDto: NewsFeedResponseDto): List<FeedPost> {
        val result = mutableListOf<FeedPost>()

        val posts = responseDto.response.newsFeeds
        val groups = responseDto.response.groups

        for (post in posts) {
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: continue
            val feedPost = FeedPost(
                id = post.id,
                comunityId = post.communityId,
                communityName = group.name,
                publicationDate = convertTime(post.date * 1000),
                communityImageUrl = group.imageUrl,
                contentText = post.text,
                contentImageUrl = post.attachments?.firstOrNull()?.photo?.lisPhotoUrl?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticItem(type = StatisticType.LIKES, post.likes.count),
                    StatisticItem(type = StatisticType.VIEWS, post.views.count),
                    StatisticItem(type = StatisticType.SHARES, post.reposts.count),
                    StatisticItem(type = StatisticType.COMMENTS, post.comments.count)
                ),
                isLiked = post.likes.userLike > 0
            )
            result.add(feedPost)
        }
        return result
    }

    fun mapResponseToPostComments(responseDto: CommentsResponseDto): List<PostComment> {
        val comments = responseDto.itemDto.comments
        val profiles = responseDto.itemDto.profiles
        val result = mutableListOf<PostComment>()

        for (comment in comments) {
            if (comment.text.isBlank()) continue
            val profile = profiles.firstOrNull { comment.authorId == it.id } ?: continue
            val postComment = PostComment(
                id = comment.id,
                authorName = profile.firsName + " " + profile.lastName,
                authorAvatar = profile.photo,
                commentText = comment.text,
                publicationData = convertTime(comment.date * 1000)
            )
            result.add(postComment)
        }
        return result
    }

    private fun convertTime(timeStem: Long): String {
        val date = Date(timeStem)
        return SimpleDateFormat("d MMMM yyyy, hh:mm", java.util.Locale.getDefault()).format(date)
    }
}