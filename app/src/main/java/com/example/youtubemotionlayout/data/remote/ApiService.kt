package com.example.youtubemotionlayout.data.remote

import com.example.youtubemotionlayout.data.remote.response.Playlist
import com.example.youtubemotionlayout.data.remote.response.VideoList
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/v3/66f12ec7-a2e6-4070-b7cc-7f3563fbe962")
    suspend fun listVideos(): VideoList

    @GET("playlistItems?key=AIzaSyBCTyw3FkGRc0nTufD65z3p6gto1t1v0pE&playlistId=PL4fGSI1pDJn6puJdseH2Rt9sMvt9E2M4i&part=snippet&maxResults=50")
    suspend fun getListVideo(): Playlist
}