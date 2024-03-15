package com.example.vkapifeedposts.navigation

import android.net.Uri
import com.google.gson.Gson
import com.example.vkapifeedposts.domain.FeedPost

sealed class Screen(
    val route:String
) {

    object Favourite : Screen(RouteFavourite)
    object Profile : Screen(RouteProfile)
    object Home : Screen(RouteHome)
    object NewsFeed : Screen(RouteNewsFeed)
    object Comments : Screen(RouteComments) {
        const val RouteForArgs = "comments"
        fun getfeedPostId(feedPost: FeedPost):String {
            val feedPostJson = Gson().toJson(feedPost)
            return (RouteForArgs + "/" + feedPostJson.encode())
        }
    }

    companion object {

        const val KeyFeedPost = "feed_post"
        const val RouteHome = "home"
        const val RouteComments = "comments/{$KeyFeedPost}"
        const val RouteNewsFeed = "news_feed"
        const val RouteFavourite = "favourite"
        const val RouteProfile= "profile"


    }
    fun String.encode() : String {
        return Uri.encode(this)
    }

}


