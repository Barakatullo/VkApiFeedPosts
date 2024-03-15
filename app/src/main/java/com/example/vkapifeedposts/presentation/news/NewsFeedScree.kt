package com.example.vkapifeedposts.presentation.news

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vkapifeedposts.ui.theme.DarkBlue
import com.example.vkapifeedposts.domain.FeedPost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsFeedScreem(
               paddingValues: PaddingValues,
               onCommentClickListener : (FeedPost) -> Unit
               ) {
    val viewModel : NewsFeedViewModel = viewModel()

    val screenState = viewModel.screenState.collectAsState(NewsFeedScreenState.Initial)
    when(val currentState = screenState.value) {
        is NewsFeedScreenState.Posts -> {
            FeedPosts(
                feedPosts = currentState.feedposts,
                paddingValues = paddingValues,
                viewModel = viewModel
          , onCommentClickListener = onCommentClickListener,
                nextDataIsLooading = currentState.nextDataIsLoading
            )
        }
        is NewsFeedScreenState.Initial -> {
        }

        NewsFeedScreenState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = DarkBlue)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FeedPosts(
    feedPosts : List<FeedPost>,
    paddingValues: PaddingValues,
    viewModel: NewsFeedViewModel,
    onCommentClickListener : (FeedPost) -> Unit,
    nextDataIsLooading:Boolean

) {
    LazyColumn(
        modifier = androidx.compose.ui.Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = feedPosts,
            key = { it.id }
        ) { feedPost ->
            val dismissState = rememberSwipeToDismissBoxState()
            if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
                viewModel.remove(feedPost)
            }


            SwipeToDismissBox(
                modifier = Modifier.animateContentSize(),
                state = dismissState,
                enableDismissFromStartToEnd = false,
                backgroundContent = {
                    Text(text = "")
                }
            ) {

                PostCard(
                    feedPost = feedPost,
                    onCommentClickListener = {
                       onCommentClickListener(feedPost)
                    },
                    onLikeClickListener = {
                        viewModel.chancgeLikeStatus(feedPost)
                    },
                )
            }
        }
        item {
            if (nextDataIsLooading) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = DarkBlue)
                }
            } else {
                SideEffect {
                    viewModel.loadNextRecommendations()
                }

            }
        }
    }
}