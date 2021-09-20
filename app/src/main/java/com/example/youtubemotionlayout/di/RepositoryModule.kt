package com.example.youtubemotionlayout.di

import com.example.youtubemotionlayout.data.remote.ApiService
import com.example.youtubemotionlayout.repositories.VideoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideVideoRepository(apiService: ApiService) = VideoRepository(apiService)
}