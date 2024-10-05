package com.example.task_innovation.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task_innovation.domain.useCase.GetAllPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePostsViewModel @Inject constructor(
    private val getPostsUseCase: GetAllPostsUseCase

) : ViewModel() {
    private val _viewState = MutableStateFlow(PostsHomeState())
    val viewState: StateFlow<PostsHomeState> = _viewState

    init {
        handleIntent(PostsHomeIntent.LoadPosts)
    }

    fun handleIntent(intent: PostsHomeIntent) {
        when (intent) {
            PostsHomeIntent.LoadPosts -> getAllPosts()
        }
    }


   private  fun getAllPosts(
        query: String = "sport",
    ) = viewModelScope.launch {
        _viewState.value = _viewState.value.copy(isLoading = true)
        try {
            val posts = getPostsUseCase.invoke(query)
            _viewState.value = _viewState.value.copy(isLoading = false, posts = posts)
        } catch (e: Exception) {
            _viewState.value = _viewState.value.copy(isLoading = false, error = e.message)
        }

    }

}