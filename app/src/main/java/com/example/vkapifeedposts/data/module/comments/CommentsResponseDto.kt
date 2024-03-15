package com.example.vkapifeedposts.data.module.comments

import com.google.gson.annotations.SerializedName

data class CommentsResponseDto(
    @SerializedName("response") val itemDto: CommentsItemDto
)
