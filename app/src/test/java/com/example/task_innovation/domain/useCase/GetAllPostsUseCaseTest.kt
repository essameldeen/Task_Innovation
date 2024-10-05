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

class GetAllPostsUseCaseTest{
    private lateinit var getAllPostsUseCase: GetAllPostsUseCase
    private val repository: PostsRepository = mockk()


    @Before
    fun setup(){
        getAllPostsUseCase = GetAllPostsUseCase(repository)
    }

    @Test
    fun `invoke should return list of posts from repository`() = runTest {

        val posts = listOf(mockk<Posts>(), mockk<Posts>())
        coEvery { repository.getPosts("sport") } returns posts


        val result = getAllPostsUseCase.invoke("sport")


        coVerify { repository.getPosts("sport") }
        assertEquals(posts, result)
    }


}