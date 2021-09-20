package com.example.youtubemotionlayout.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubemotionlayout.data.remote.response.Item
import com.example.youtubemotionlayout.data.remote.response.Playlist
import com.example.youtubemotionlayout.data.remote.response.VideoList
import com.example.youtubemotionlayout.repositories.VideoRepository
import com.example.youtubemotionlayout.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val videoRepository: VideoRepository): ViewModel() {
    val videoListResponse: MutableLiveData<Playlist> by lazy { MutableLiveData() }
    val loading: MutableLiveData<Boolean> by lazy { MutableLiveData() }

    fun getVideoList() {
        viewModelScope.launch {
            loading.value = true
            val result = videoRepository.getListVideo()
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        Log.e("response", it.items?.size.toString())
                        videoListResponse.value = it
                    }
                    loading.value = false
                }

                is Resource.Error -> {
                    loading.value = false
                }

                is Resource.Loading -> {

                }
            }
        }
    }
}