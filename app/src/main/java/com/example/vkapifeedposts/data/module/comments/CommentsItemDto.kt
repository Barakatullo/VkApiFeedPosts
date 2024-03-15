package com.example.vkapifeedposts.data.module.comments

import com.google.gson.annotations.SerializedName

data class CommentsItemDto(
    @SerializedName("items") val comments : List<CommentsDto>,
    @SerializedName("profiles") val profiles : List<ProfilesDto>
)
