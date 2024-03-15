package com.example.vkapifeedposts.data.module

import com.google.gson.annotations.SerializedName

data class PhotoDto(
    @SerializedName("sizes") val lisPhotoUrl : List<PhotoUrlDto>
)
