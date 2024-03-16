package com.example.vkapifeedposts.di

import com.example.vkapifeedposts.domain.entity.FeedPost
import com.example.vkapifeedposts.presentation.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [CommentsViewModuleModule::class])
interface CommentsScreenComponent {
    fun getVMFactory(): ViewModelFactory
    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance feedPost: FeedPost):CommentsScreenComponent
    }
}