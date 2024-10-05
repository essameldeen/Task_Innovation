package com.example.task_innovation.domain.useCase

import com.example.task_innovation.domain.models.Posts
import com.example.task_innovation.domain.repository.PostsRepository
import javax.inject.Inject

class GetPostByIdUseCase @Inject constructor(
    private val postsRepository: PostsRepository
) {

    suspend fun invoke(id: Int): Posts {
        return postsRepository.getPostById(id)
    }
}