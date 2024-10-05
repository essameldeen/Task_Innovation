package com.example.task_innovation.domain.useCase


import com.example.task_innovation.domain.models.Posts
import com.example.task_innovation.domain.repository.PostsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetPostByIdUseCaseTest {
    private lateinit var getPostByIdUseCase: GetPostByIdUseCase
    private val postRepository: PostsRepository = mockk()


    @Before
    fun setup() {
        getPostByIdUseCase = GetPostByIdUseCase(postRepository)
    }

    @Test
    fun `invoke should return article by id from articles from repository `() = runTest {
        val post = mockk<Posts>()
        coEvery { postRepository.getPostById(1) } returns post


        val result = getPostByIdUseCase.invoke(1)


        coVerify { postRepository.getPostById(1) }
        assertEquals(post, result)
    }
}