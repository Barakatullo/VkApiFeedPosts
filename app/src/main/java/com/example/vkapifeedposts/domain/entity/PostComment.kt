package com.example.vkapifeedposts.domain.entity




data class PostComment(
val id: Long,
val authorName: String,
val authorAvatar: String,
val commentText: String,
val publicationData : String
)