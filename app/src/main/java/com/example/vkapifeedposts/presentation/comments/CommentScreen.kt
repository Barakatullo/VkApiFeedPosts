package com.example.vkapifeedposts.presentation.comments

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.vkapifeedposts.domain.PostComment
import com.example.vkapifeedposts.domain.FeedPost

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CommentScreen(
    feedPost: FeedPost,
    onBackPressed: () -> Unit) {
    val context = LocalContext.current
    val application = context.applicationContext as Application
    val viewmodel: CommentsViewModel = viewModel(
        factory = CommentsViewModelFactory(
            feedPost,
            application
        )
    )

    val screenState = viewmodel.screenState.observeAsState(CommentsScreenState.Initial)

    val currentState = screenState.value
    if (currentState is CommentsScreenState.Comments) {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(
                        text = "Comments:",
                        color = Color.Blue,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },

                    navigationIcon = {
                        IconButton(onClick = {
                            onBackPressed()
                        }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier.padding(it),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 72.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = currentState.comments , key = { it.id }) {
                    Comment(comment = it)
                }

            }
        }
    }
}

@Composable
fun Comment(comment: PostComment){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 4.dp)

    ) {
        AsyncImage(model = comment.authorAvatar ,modifier = Modifier.size(48.dp).
            clip(CircleShape), contentDescription =null )
        Spacer(modifier = Modifier.width(8.dp))
        Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text(text =comment.authorName, color = Color.Cyan,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
            Text(text = comment.commentText, color = Color.Blue,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text =comment.publicationData, color = Color.Yellow,
                fontSize = 12.sp
            )
        }

    }
}