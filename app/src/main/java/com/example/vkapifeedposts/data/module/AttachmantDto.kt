package com.example.vkapifeedposts.data.module

import com.google.gson.annotations.SerializedName

data class AttachmantDto(
    @SerializedName("photo") val photo: PhotoDto?
)
