package com.example.vkapifeedposts.data.module

import com.example.vkapifeedposts.data.module.LikesCountDto
import com.google.gson.annotations.SerializedName

data class LikesCountResponse(
    @SerializedName("response") val likes: LikesCountDto
)