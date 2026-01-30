package com.example.compose.domain.usecase

import com.example.compose.common.Resource
import com.example.compose.domain.model.Story
import com.example.compose.domain.repository.StoriesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetStoriesUseCaseTest {

    private val repo: StoriesRepository = mockk()
    private val useCase = GetStoriesUseCase(repo)

    @Test
    fun `invoke returns posts from repository`() = runTest {
        val stories = listOf(
            Story(
                id = 1,
                title = "S1",
                cover = "c1"
            )
        )
        val expected = Resource.Success(stories)

        coEvery { repo.getStories() } returns expected

        val result = useCase()

        assertEquals(expected, result)
        coVerify(exactly = 1) { repo.getStories() }
    }

    @Test
    fun `invoke returns error from repository`() = runTest {
        val expected = Resource.Error("boom")

        coEvery { repo.getStories() } returns expected

        val result = useCase()

        assertEquals(expected, result)
        coVerify(exactly = 1) { repo.getStories() }
    }
}
