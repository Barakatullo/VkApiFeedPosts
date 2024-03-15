package com.example.vkapifeedposts.data.module

import com.google.gson.annotations.SerializedName

data class FeedPostDto(
    @SerializedName("source_id") val communityId:Long,
    @SerializedName("id") val id:Long,
    @SerializedName("date") val date:Long,
    @SerializedName("text") val text:String,
    @SerializedName("likes") val likes: LikesDto,
    @SerializedName("comments") val comments: CommentsDto,
    @SerializedName("views") val views: ViewsDto,
    @SerializedName("reposts") val reposts: RepostsDto,
    @SerializedName("attachments") val attachments: List<AttachmantDto>?
)