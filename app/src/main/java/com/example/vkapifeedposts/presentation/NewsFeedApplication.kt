package com.example.vkapifeedposts.presentation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.vkapifeedposts.di.ApplicationComponent
import com.example.vkapifeedposts.di.DaggerApplicationComponent

class NewsFeedApplication : Application() {
    val component : ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}
@Composable
fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as NewsFeedApplication).component
}
