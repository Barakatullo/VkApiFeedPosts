package com.example.vkapifeedposts.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vkapifeedposts.R

@Composable
fun LoginScreen(
     onLoginClickListener : () -> Unit
){
    Box(modifier = Modifier.fillMaxSize()
    , contentAlignment = Alignment.Center
    ) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        , modifier = Modifier) {
            Image(painter = painterResource(id = R.drawable.instagram_1_svgrepo_com), contentDescription = null,
                modifier = Modifier.size(100.dp)
                )
            Spacer(modifier = Modifier.height(100.dp))
            Button(onClick = { onLoginClickListener() }) {
                Text(text = "Войти")
            }
        }
    }
}