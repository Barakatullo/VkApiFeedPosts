package com.example.vkapifeedposts.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.gson.Gson
import com.example.vkapifeedposts.domain.entity.FeedPost


fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent : @Composable () -> Unit,
    commentsScreenContext : @Composable (FeedPost) -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route =  Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route,) {
            newsFeedScreenContent()
        }
        composable(route = Screen.Comments.route,
            arguments = listOf(
                navArgument(Screen.KeyFeedPost) {
                    type = NavType.StringType
                }
            )
            ){
             val feedPostJson = it.arguments?.getString(Screen.KeyFeedPost) ?:""

            val feedPost = Gson().fromJson(feedPostJson, FeedPost::class.java)
            commentsScreenContext(feedPost)
        }

    }
}