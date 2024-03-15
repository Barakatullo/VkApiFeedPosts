package com.example.vkapifeedposts.data.module

import com.example.vkapifeedposts.data.module.NewsFeedContentDto
import com.google.gson.annotations.SerializedName

data class NewsFeedResponseDto(
    @SerializedName("response") val response: NewsFeedContentDto
)
