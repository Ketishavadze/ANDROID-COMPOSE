package com.example.compose.ui.screen.home


import androidx.lifecycle.viewModelScope
import com.example.compose.common.Resource
import com.example.compose.domain.usecase.GetPostsUseCase
import com.example.compose.domain.usecase.GetStoriesUseCase
import com.example.compose.ui.common.BaseViewModel
import com.example.compose.ui.screen.home.contract.HomeEvent
import com.example.compose.ui.screen.home.contract.HomeSideEffect
import com.example.compose.ui.screen.home.contract.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getStoriesUseCase: GetStoriesUseCase,
    private val getPostsUseCase: GetPostsUseCase
) : BaseViewModel<HomeState, HomeEvent, HomeSideEffect>(
    initialState = HomeState()
) {

    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LoadInitial -> loadData(false)
            HomeEvent.Refresh -> loadData(true)
        }
    }

    private fun loadData(isRefresh: Boolean) {
        viewModelScope.launch {
            if (!isRefresh) updateState { it.copy(isLoading = true) }

            val storiesRes = getStoriesUseCase()
            val postsRes = getPostsUseCase()

            when (storiesRes) {
                is Resource.Success -> updateState { it.copy(stories = storiesRes.data ?: emptyList()) }
                is Resource.Error -> emitSideEffect(HomeSideEffect.ShowError(storiesRes.errorMessage))
                else -> {}
            }

            when (postsRes) {
                is Resource.Success -> updateState { it.copy(posts = postsRes.data ?: emptyList()) }
                is Resource.Error -> emitSideEffect(HomeSideEffect.ShowError(postsRes.errorMessage))
                else -> {}
            }

            updateState { it.copy(isLoading = false) }
        }
    }
}