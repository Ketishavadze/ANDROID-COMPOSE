package com.example.compose.domain.usecase

import com.example.compose.common.Resource
import com.example.compose.domain.model.Post
import com.example.compose.domain.repository.PostsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetPostsUseCaseTest {

    private val repo: PostsRepository = mockk()
    private val useCase = GetPostsUseCase(repo)

    @Test
    fun `invoke returns posts from repository`() = runTest {
        val posts = listOf(
            Post(
                id = 1,
                fullName = "Alice",
                avatar = null,
                postDate = 1L,
                postDesc = "Hi",
                images = emptyList(),
                commentsCount = 0,
                likesCount = 0,
                isLiked = false
            )
        )
        val expected = Resource.Success(posts)

        coEvery { repo.getPosts() } returns expected

        val result = useCase()

        assertEquals(expected, result)
        coVerify(exactly = 1) { repo.getPosts() }
    }

    @Test
    fun `invoke returns error from repository`() = runTest {
        val expected = Resource.Error("boom")

        coEvery { repo.getPosts() } returns expected

        val result = useCase()

        assertEquals(expected, result)
        coVerify(exactly = 1) { repo.getPosts() }
    }
}
