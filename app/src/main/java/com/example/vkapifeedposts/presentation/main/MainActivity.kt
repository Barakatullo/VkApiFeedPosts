package com.example.vkapifeedposts.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vkapifeedposts.ui.theme.VkApiFeedPostsTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkApiFeedPostsTheme {
                val viewModel : LoginViewModel = viewModel()
                val stateOf = viewModel.authState.observeAsState(LoginState.Initial)
                val launch = rememberLauncherForActivityResult(contract = VK.getVKAuthActivityResultContract() ) {
                    viewModel.performAuthResult(it)
                }
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