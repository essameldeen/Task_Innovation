package com.example.task_innovation.domain.repository

import com.example.task_innovation.domain.models.Posts

interface PostsRepository {
    suspend fun getPosts(
        searchQuery: String? = null
    ): List<Posts>

    suspend fun getPostById(
        id: Int
    ): Posts

}