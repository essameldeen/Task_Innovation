package com.example.task_innovation.data.dataSource.remote.response
data class  PostsResponse(
    val articles: List<PostDto>,
    val status: String,
    val totalResults: Int
)

data class PostDto(
    val content: String?,
    val description: String?,
    val title: String?,
    val urlToImage: String?,
)

