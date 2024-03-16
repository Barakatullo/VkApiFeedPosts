package com.example.vkapifeedposts.di

import androidx.lifecycle.ViewModel
import com.example.vkapifeedposts.presentation.comments.CommentsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CommentsViewModuleModule {
    @IntoMap
    @ViewModelKey(CommentsViewModel::class)
    @Binds
    fun bindsCommensVM(v: CommentsViewModel): ViewModel
}