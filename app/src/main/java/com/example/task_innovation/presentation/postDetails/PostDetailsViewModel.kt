package com.example.task_innovation.presentation.postDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.task_innovation.domain.useCase.GetPostByIdUseCase
import com.example.task_innovation.presentation.home.PostsHomeIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val getPostById: GetPostByIdUseCase,


) : ViewModel() {

    private val _viewState = MutableStateFlow(DetailsViewState())
    val viewState: StateFlow<DetailsViewState> = _viewState



    fun handleIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.LoadPostDetails -> loadPostDetails(intent.postId)
        }
    }

    fun loadPostDetails(postId: Int) = viewModelScope.launch {
        _viewState.value = DetailsViewState(isLoading = true)

        try {
            val post = getPostById.invoke(postId)
            _viewState.value = DetailsViewState(post = post)
        } catch (e: Exception) {
            _viewState.value = DetailsViewState(error = e.message)
        }
    }



}

