package com.example.youtubemotionlayout.data.remote.response


import com.google.gson.annotations.SerializedName

data class ResourceId(
    @SerializedName("kind")
    val kind: String?,
    @SerializedName("videoId")
    val videoId: String?
)