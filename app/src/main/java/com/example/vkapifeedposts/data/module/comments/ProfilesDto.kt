package com.example.vkapifeedposts.data.module.comments

import com.google.gson.annotations.SerializedName

data class ProfilesDto(
    @SerializedName("id") val id:Long,
    @SerializedName("first_name") val firsName :String,
    @SerializedName("last_name") val lastName :String,
    @SerializedName("photo_100") val photo :String,
)
