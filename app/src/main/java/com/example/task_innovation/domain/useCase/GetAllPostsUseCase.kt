package com.example.task_innovation.domain.useCase

import com.example.task_innovation.domain.models.Posts
import com.example.task_innovation.domain.repository.PostsRepository
import javax.inject.Inject


class GetAllPostsUseCase @Inject constructor  (
     private val postsRepository: PostsRepository
) {
    suspend operator fun invoke(
        category: String
    ): List<Posts> =postsRepository.getPosts(category)

}