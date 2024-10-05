package com.example.task_innovation.presentation.postDetails

import com.example.task_innovation.domain.models.Posts

data class DetailsViewState(
    val isLoading: Boolean = false,
    val post: Posts? = null,
    val error: String? = null,
)

sealed class DetailsIntent {
    data class LoadPostDetails(val postId: Int) : DetailsIntent()
}