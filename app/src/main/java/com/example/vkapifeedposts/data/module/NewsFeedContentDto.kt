package com.example.vkapifeedposts.data.module

import com.example.vkapifeedposts.data.module.FeedPostDto
import com.example.vkapifeedposts.data.module.GroupDto
import com.google.gson.annotations.SerializedName

class NewsFeedContentDto(
    @SerializedName("items") val newsFeeds: List<FeedPostDto>,
    @SerializedName("groups") val groups : List<GroupDto>,
    @SerializedName("next_from") val nextFrom: String?
)
