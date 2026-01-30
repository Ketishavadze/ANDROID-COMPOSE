package com.example.compose.data.mapper


import com.example.compose.data.remote.model.PostDto
import org.junit.Assert.*
import org.junit.Test

class PostMapperTest {

    @Test
    fun `toDomain maps fields correctly`() {
        val dto = PostDto(
            id = 1,
            avatar = "https://x.y/a.png",
            postDate = 123L,
            postDesc = "Hello",
            images = listOf("i1", "i2"),
            commentsCount = 7,
            likesCount = 49,
            canComment = true,
            canPostPhoto = true,
            firstName = "Ketevaaaaaaaaan",
            lastName = "Shava",
        )

        val domain = dto.toDomain()

        assertEquals(1, domain.id)
        assertEquals("Ketevaaaaaaaaan Shava", domain.fullName)
        assertEquals("https://x.y/a.png", domain.avatar)
        assertEquals(123L, domain.postDate)
        assertEquals("Hello", domain.postDesc)
        assertEquals(listOf("i1", "i2"), domain.images)
        assertEquals(7, domain.commentsCount)
        assertEquals(49, domain.likesCount)
        assertTrue(domain.isLiked)
    }

    @Test
    fun `toDomain allows null avatar`() {
        val dto = PostDto(
            id = 1,
            avatar = "https://x.y/a.png",
            postDate = 123L,
            postDesc = "Hello",
            images = listOf("i1", "i2"),
            commentsCount = 7,
            likesCount = 49,
            canComment = true,
            canPostPhoto = true,
            firstName = "Ketevaaaaaaaaan",
            lastName = "Shava",
        )

        val domain = dto.toDomain()
        assertNull(domain.avatar)
    }
}
