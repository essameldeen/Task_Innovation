package com.example.task_innovation.presentation.postDetails

import com.example.task_innovation.coroutines.MainDispatcherRule
import com.example.task_innovation.domain.models.Posts
import com.example.task_innovation.domain.useCase.GetAllPostsUseCase
import com.example.task_innovation.domain.useCase.GetPostByIdUseCase
import com.example.task_innovation.presentation.home.HomePostsViewModel
import com.example.task_innovation.presentation.home.PostsHomeIntent
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
@OptIn(ExperimentalCoroutinesApi::class)
class PostDetailsViewModelTest{
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    private  var useCase: GetPostByIdUseCase = mockk()

    private lateinit var viewModel: PostDetailsViewModel

   private val result =  Posts("pos1", urlToImage = "post1", id = 1)

    @Before
    fun setup() {
        coEvery { useCase.invoke(any()) } returns result
    }


    @Test
    fun `initial state should be loading and then loaded with post`() = runTest {
        viewModel = PostDetailsViewModel(useCase)


        assertEquals(false, viewModel.viewState.first().isLoading)

        advanceUntilIdle()


        assertEquals(false, viewModel.viewState.first().isLoading)
        assertEquals(null, viewModel.viewState.first().post)
    }
    @Test
    fun `getPostListings should update state with post`() = runTest {
        coEvery { useCase.invoke(1) } returns result

        viewModel = PostDetailsViewModel(useCase)
        viewModel.handleIntent(DetailsIntent.LoadPostDetails(1))


        advanceUntilIdle()


        val finalState = viewModel.viewState.first()
        assertEquals(false, finalState.isLoading)
        assertEquals(result, finalState.post)
    }

    @Test
    fun `getPostListings should update state with error on failure`() = runTest {
        val errorMessage = "Error occurred"
        coEvery { useCase.invoke(1) } throws Exception(errorMessage)

        viewModel = PostDetailsViewModel(useCase)
        viewModel.handleIntent(DetailsIntent.LoadPostDetails(1))


        advanceUntilIdle()


        val finalState = viewModel.viewState.first()
        assertEquals(false, finalState.isLoading)
        assertEquals(errorMessage, finalState.error)
    }
}