package com.sumin.vknewsclient.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vkapifeedposts.navigation.homeScreenNavGraph
import com.example.vkapifeedposts.domain.entity.FeedPost
import com.example.vkapifeedposts.navigation.Screen


@Composable
fun AppNavGraph(navController: NavHostController,
                newsFeedScreenContent : @Composable () -> Unit,
                favouriteScreenContent : @Composable () -> Unit,
                profileScreenContent : @Composable () -> Unit,
                commentsScreenContext : @Composable (FeedPost) -> Unit,
                ) {
   NavHost(
       navController = navController,
       startDestination = Screen.Home.route,
       builder =
       {

       homeScreenNavGraph(
           newsFeedScreenContent = newsFeedScreenContent,
           commentsScreenContext = commentsScreenContext
       )

       composable(Screen.Favourite.route,) {
           favouriteScreenContent()
       }
       composable(Screen.Profile.route,) {
           profileScreenContent()
       }

   } )
}