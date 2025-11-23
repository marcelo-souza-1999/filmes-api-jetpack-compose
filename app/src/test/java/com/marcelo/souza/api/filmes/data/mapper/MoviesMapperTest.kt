package com.marcelo.souza.api.filmes.data.mapper

import com.marcelo.souza.api.filmes.data.model.CategoryResponse
import com.marcelo.souza.api.filmes.data.model.CoverResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class MoviesMapperTest {

    private fun sampleCoverResponse() = CoverResponse(
        id = 10,
        imageUrl = "https://example.com/img.jpg",
        name = "Filme X",
        description = "Descrição X",
        cast = "Atores X"
    )

    private fun sampleCategoryResponse() = CategoryResponse(
        id = 1,
        title = "Ação",
        covers = listOf(sampleCoverResponse())
    )

    @Test
    fun `mapCategoryResponseToViewData maps fields correctly`() {
        val response = sampleCategoryResponse()

        val result = MoviesMapper.mapCategoryResponseToViewData(response)

        assertEquals(1, result.id)
        assertEquals("Ação", result.title)
        assertEquals(1, result.cover.size)

        val detail = result.cover[0]
        assertEquals(10, detail.id)
        assertEquals("https://example.com/img.jpg", detail.imageUrl)
        assertEquals("Filme X", detail.name)
        assertEquals("Descrição X", detail.description)
        assertEquals("Atores X", detail.cast)
    }

    @Test
    fun `mapCategoryResponseToViewData maps empty covers list`() {
        val response = CategoryResponse(
            id = 2,
            title = "Drama",
            covers = emptyList()
        )

        val result = MoviesMapper.mapCategoryResponseToViewData(response)

        assertEquals(2, result.id)
        assertEquals("Drama", result.title)
        assertEquals(0, result.cover.size)
    }

    @Test
    fun `mapCoverResponseToViewData maps fields correctly`() {
        val cover = sampleCoverResponse()

        val category = CategoryResponse(
            id = 99,
            title = "Test",
            covers = listOf(cover)
        )

        val result = MoviesMapper.mapCategoryResponseToViewData(category)
        val mapped = result.cover.first()

        assertEquals(cover.id, mapped.id)
        assertEquals(cover.imageUrl, mapped.imageUrl)
        assertEquals(cover.name, mapped.name)
        assertEquals(cover.description, mapped.description)
        assertEquals(cover.cast, mapped.cast)
    }
}