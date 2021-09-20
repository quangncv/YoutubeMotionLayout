package com.example.youtubemotionlayout.data.remote.response


import com.google.gson.annotations.SerializedName

data class Playlist(
    @SerializedName("etag")
    val etag: String?,
    @SerializedName("items")
    val items: List<Item>?,
    @SerializedName("kind")
    val kind: String?,
    @SerializedName("nextPageToken")
    val nextPageToken: String?
)