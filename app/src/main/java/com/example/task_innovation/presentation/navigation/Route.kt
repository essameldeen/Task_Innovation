package com.example.task_innovation.presentation.navigation

sealed class Route(
    val route: String
) {
     object PostsNavigation : Route("postsNavigation")
     object PostsNavigatorScreen : Route("postsNavigatorScreen")
     object DetailsScreen : Route("detailsScreen")
}