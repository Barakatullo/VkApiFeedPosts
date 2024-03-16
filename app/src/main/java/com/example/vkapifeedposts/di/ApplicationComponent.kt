package com.example.vkapifeedposts.di

import android.content.Context
import com.example.vkapifeedposts.domain.entity.FeedPost
import com.example.vkapifeedposts.presentation.main.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)
    fun getCommentsScreenComponentFactory():CommentsScreenComponent.Factory
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ) : ApplicationComponent
    }

}