package com.example.task_innovation.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.task_innovation.presentation.postDetails.DetailsIntent
import com.example.task_innovation.presentation.postDetails.PostDetailsScreen
import com.example.task_innovation.presentation.postDetails.PostDetailsViewModel
import com.example.task_innovation.presentation.home.HomePostsViewModel
import com.example.task_innovation.presentation.home.HomeScreen

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {

        navigation(
            route = Route.PostsNavigation.route,
            startDestination = Route.PostsNavigatorScreen.route
        ) {
            composable(route = Route.PostsNavigatorScreen.route) {

                val viewModel: HomePostsViewModel = hiltViewModel()
                HomeScreen(viewModel, navigateToDetails = { id ->
                    navigateToDetails(navController, id)
                })

            }
            composable(route = Route.DetailsScreen.route) {
                val viewModel: PostDetailsViewModel = hiltViewModel()
                navController.previousBackStackEntry?.savedStateHandle?.get<Int>("postId")
                    ?.let { postId ->
                        viewModel.handleIntent(DetailsIntent.LoadPostDetails(postId))
                        PostDetailsScreen(viewModel = viewModel) {
                            navController.navigateUp()
                        }
                    }

            }

        }
    }

}
private fun navigateToDetails(navController: NavController, id: Int) {
    navController.currentBackStackEntry?.savedStateHandle?.set("postId", id)
    navController.navigate(Route.DetailsScreen.route)
}
