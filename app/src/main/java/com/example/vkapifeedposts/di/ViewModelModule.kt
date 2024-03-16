package com.example.vkapifeedposts.di

import androidx.lifecycle.ViewModel
import com.example.vkapifeedposts.presentation.comments.CommentsViewModel
import com.example.vkapifeedposts.presentation.main.MainViewModel
import com.example.vkapifeedposts.presentation.news.NewsFeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(NewsFeedViewModel::class)
    @Binds
    fun bindsNewsFeedVM(v:NewsFeedViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindsMainVM(v:MainViewModel): ViewModel
}