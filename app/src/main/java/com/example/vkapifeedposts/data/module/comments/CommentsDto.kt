package com.example.vkapifeedposts.data.module.comments

import com.google.gson.annotations.SerializedName

data class CommentsDto(
    @SerializedName("id") val id: Long,
    @SerializedName("from_id") val authorId: Long,
    @SerializedName("text") val text: String,
    @SerializedName("date") val date: Long,
)
