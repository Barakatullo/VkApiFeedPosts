package com.example.vkapifeedposts.di

import android.app.Application
import android.content.Context
import com.example.vkapifeedposts.data.network.ApiFactory
import com.example.vkapifeedposts.data.network.ApiService
import com.example.vkapifeedposts.data.repository.NewsFeedRepositoryImpl
import com.example.vkapifeedposts.domain.repository.NewsFeedRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @ApplicationScope
    @Binds
    fun bindsRepository(impl:NewsFeedRepositoryImpl): NewsFeedRepository

    companion object {
        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService{
            return ApiFactory.apiService
        }
        @ApplicationScope
        @Provides
        fun provideVkStorage(context:Context): VKPreferencesKeyValueStorage{
            return  VKPreferencesKeyValueStorage(context)
        }
    }
}