package com.example.youtubemotionlayout.repositories

import com.example.youtubemotionlayout.data.remote.ApiService
import com.example.youtubemotionlayout.data.remote.response.Playlist
import com.example.youtubemotionlayout.data.remote.response.VideoList
import com.example.youtubemotionlayout.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class VideoRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getVideoList(): Resource<VideoList> {
        val response = try {
            apiService.listVideos()
        } catch (ex: Exception) {
            return Resource.Error("An unknown error occured")
        }

        return Resource.Success(response)
    }

    suspend fun getListVideo(): Resource<Playlist> {
        val response = try {
            apiService.getListVideo()
        } catch (ex: Exception) {
            return Resource.Error("An unknown error occured")
        }

        return Resource.Success(response)
    }
}