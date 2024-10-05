package com.example.task_innovation.presentation.home
import com.example.task_innovation.coroutines.MainDispatcherRule
import com.example.task_innovation.domain.models.Posts
import com.example.task_innovation.domain.useCase.GetAllPostsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
@OptIn(ExperimentalCoroutinesApi::class)
class HomePostsViewModelTest{
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    private  var useCase: GetAllPostsUseCase = mockk()

    private lateinit var viewModel: HomePostsViewModel


    @Before
    fun setup() {
        coEvery { useCase.invoke(any()) } returns emptyList()
    }



    @Test
    fun `initial state should be loading and then loaded with posts`() = runTest {
        viewModel = HomePostsViewModel(useCase)


        assertEquals(false, viewModel.viewState.first().isLoading)

        advanceUntilIdle()


        assertEquals(false, viewModel.viewState.first().isLoading)
        assertEquals(emptyList<Posts>(), viewModel.viewState.first().posts)
    }

    @Test
    fun `getArticleListings should update state with articles`() = runTest {
        val result = listOf(Posts(
            "post1",
            "image1",
            1,

        )
        )
        coEvery { useCase.invoke(any()) } returns result

        viewModel = HomePostsViewModel(useCase)


        advanceUntilIdle()


        val finalState = viewModel.viewState.first()
        assertEquals(false, finalState.isLoading)
        assertEquals(result, finalState.posts)
    }
    @Test
    fun `getPostsListings should update state with error on failure`() = runTest {
        val errorMessage = "Error occurred"
        coEvery { useCase.invoke(any()) } throws Exception(errorMessage)

        viewModel = HomePostsViewModel(useCase)


        advanceUntilIdle()


        val finalState = viewModel.viewState.first()
        assertEquals(false, finalState.isLoading)
        assertEquals(errorMessage, finalState.error)
    }
}