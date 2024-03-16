package com.example.vkapifeedposts.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vkapifeedposts.di.ApplicationComponent
import com.example.vkapifeedposts.domain.entity.LoginState
import com.example.vkapifeedposts.presentation.ViewModelFactory
import com.example.vkapifeedposts.presentation.news.NewsFeedApplication
import com.example.vkapifeedposts.ui.theme.VkApiFeedPostsTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewmodelFactory: ViewModelFactory
    private val component by lazy {
        (application as NewsFeedApplication).component
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            VkApiFeedPostsTheme {
                val viewModel : MainViewModel = viewModel(factory =  viewmodelFactory)
                val stateOf = viewModel.authState.collectAsState(LoginState.Initial)
                val launch = rememberLauncherForActivityResult(contract = VK.getVKAuthActivityResultContract() ) {
                    viewModel.performAuthResult()
                }
                when(stateOf.value){
                    LoginState.Auth -> {
                        MainScreen(viewmodelFactory)
                    }
                    LoginState.NoAuth -> {
                        LoginScreen {
                            launch.launch(listOf(VKScope.WALL,VKScope.FRIENDS))
                        }
                    }
                    else -> {

                    }

                }
            }
        }

    }
}