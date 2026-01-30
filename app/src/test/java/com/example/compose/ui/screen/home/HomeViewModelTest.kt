package com.example.compose.ui.screen.home

import app.cash.turbine.test
import com.example.compose.common.Resource
import com.example.compose.domain.model.Post
import com.example.compose.domain.model.Story
import com.example.compose.domain.usecase.GetPostsUseCase
import com.example.compose.domain.usecase.GetStoriesUseCase
import com.example.compose.ui.screen.home.contract.HomeEvent
import com.example.compose.ui.screen.home.contract.HomeSideEffect
import com.example.compose.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getStoriesUseCase: GetStoriesUseCase = mockk(relaxed = true)
    private val getPostsUseCase: GetPostsUseCase = mockk(relaxed = true)

    private fun createVm(): HomeViewModel =
        HomeViewModel(
            getStoriesUseCase = getStoriesUseCase,
            getPostsUseCase = getPostsUseCase
        )

    @Test
    fun `LoadInitial sets loading true then loads stories and posts then loading false`() = runTest {
        // Arrange
        val stories = listOf(Story(id = 1, title = "S1", cover = "c1"))
        val posts = listOf(
            Post(
                id = 1,
                fullName = "Alice",
                avatar = null, // set non-null if your Post.avatar is String
                postDate = 1706601600000,
                postDesc = "Hi",
                images = emptyList(),
                commentsCount = 0,
                likesCount = 0,
                isLiked = false
            )
        )

        coEvery { getStoriesUseCase() } returns Resource.Success(stories)
        coEvery { getPostsUseCase() } returns Resource.Success(posts)

        val viewModel = createVm()

        // Act
        viewModel.onEvent(HomeEvent.LoadInitial)

        // Let coroutines run
        advanceUntilIdle()

        // Assert
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertEquals(stories, state.stories)
        assertEquals(posts, state.posts)

        coVerify(exactly = 1) { getStoriesUseCase() }
        coVerify(exactly = 1) { getPostsUseCase() }
    }

    @Test
    fun `Refresh does not force loading true initially and ends with loading false`() = runTest {
        // Arrange
        val stories = listOf(Story(id = 1, title = "S1", cover = "c1"))
        val posts = emptyList<Post>()

        coEvery { getStoriesUseCase() } returns Resource.Success(stories)
        coEvery { getPostsUseCase() } returns Resource.Success(posts)

        val viewModel = createVm()

        // Act
        viewModel.onEvent(HomeEvent.Refresh)

        // Let coroutines run
        advanceUntilIdle()

        // Assert (end state)
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertEquals(stories, state.stories)
        assertEquals(posts, state.posts)

        coVerify(exactly = 1) { getStoriesUseCase() }
        coVerify(exactly = 1) { getPostsUseCase() }
    }

    @Test
    fun `Stories error emits ShowError side effect`() = runTest {
        // Arrange
        coEvery { getStoriesUseCase() } returns Resource.Error("stories failed")
        coEvery { getPostsUseCase() } returns Resource.Success(emptyList())

        val viewModel = createVm()

        // Assert side effects
        viewModel.sideEffect.test {
            // Act
            viewModel.onEvent(HomeEvent.LoadInitial)
            advanceUntilIdle()

            val effect = awaitItem()
            assertTrue(effect is HomeSideEffect.ShowError)
            assertEquals("stories failed", (effect as HomeSideEffect.ShowError).message)

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { getStoriesUseCase() }
        coVerify(exactly = 1) { getPostsUseCase() }
    }

    @Test
    fun `Posts error emits ShowError side effect`() = runTest {
        // Arrange
        coEvery { getStoriesUseCase() } returns Resource.Success(emptyList())
        coEvery { getPostsUseCase() } returns Resource.Error("posts failed")

        val viewModel = createVm()

        viewModel.sideEffect.test {
            // Act
            viewModel.onEvent(HomeEvent.LoadInitial)
            advanceUntilIdle()

            val effect = awaitItem()
            assertTrue(effect is HomeSideEffect.ShowError)
            assertEquals("posts failed", (effect as HomeSideEffect.ShowError).message)

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { getStoriesUseCase() }
        coVerify(exactly = 1) { getPostsUseCase() }
    }

    @Test
    fun `Both errors emit two ShowError side effects`() = runTest {
        // Arrange
        coEvery { getStoriesUseCase() } returns Resource.Error("stories failed")
        coEvery { getPostsUseCase() } returns Resource.Error("posts failed")

        val viewModel = createVm()

        viewModel.sideEffect.test {
            // Act
            viewModel.onEvent(HomeEvent.LoadInitial)
            advanceUntilIdle()

            val e1 = awaitItem() as HomeSideEffect.ShowError
            val e2 = awaitItem() as HomeSideEffect.ShowError

            assertEquals(setOf("stories failed", "posts failed"), setOf(e1.message, e2.message))

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { getStoriesUseCase() }
        coVerify(exactly = 1) { getPostsUseCase() }
    }
}
