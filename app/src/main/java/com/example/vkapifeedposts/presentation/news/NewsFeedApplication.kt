package com.example.vkapifeedposts.presentation.news

import android.app.Application
import com.example.vkapifeedposts.di.ApplicationComponent
import com.example.vkapifeedposts.di.DaggerApplicationComponent
import com.example.vkapifeedposts.domain.entity.FeedPost

class NewsFeedApplication : Application() {
    val component : ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}