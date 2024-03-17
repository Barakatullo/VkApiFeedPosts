package com.example.vkapifeedposts.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vkapifeedposts.domain.entity.LoginState
import com.example.vkapifeedposts.presentation.ViewModelFactory
import com.example.vkapifeedposts.presentation.NewsFeedApplication
import com.example.vkapifeedposts.presentation.getApplicationComponent
import com.example.vkapifeedposts.ui.theme.VkApiFeedPostsTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val component = getApplicationComponent()
            val viewModel : MainViewModel = viewModel(factory =  component.getViewModelFactory())
            val stateOf = viewModel.authState.collectAsState(LoginState.Initial)
            val launch = rememberLauncherForActivityResult(contract = VK.getVKAuthActivityResultContract() ) {
                viewModel.performAuthResult()
            }
            VkApiFeedPostsTheme {

                when(stateOf.value){
                    LoginState.Auth -> {
                        MainScreen()
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