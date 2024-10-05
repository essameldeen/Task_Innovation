package com.example.task_innovation.presentation.home

import com.example.task_innovation.domain.models.Posts


data class PostsHomeState(
    val isLoading: Boolean = false,
    val posts: List<Posts>? = null,
    val error: String? = null
)


sealed class PostsHomeIntent {
    object LoadPosts : PostsHomeIntent()
}

