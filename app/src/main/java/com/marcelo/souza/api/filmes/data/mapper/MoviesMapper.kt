package com.marcelo.souza.api.filmes.data.mapper

import com.marcelo.souza.api.filmes.data.model.CategoryResponse
import com.marcelo.souza.api.filmes.data.model.CoverResponse
import com.marcelo.souza.api.filmes.domain.model.CategoryViewData
import com.marcelo.souza.api.filmes.domain.model.DetailsMovieViewData

object MoviesMapper {

    fun mapCategoryResponseToViewData(response: CategoryResponse): CategoryViewData {
        return CategoryViewData(
            id = response.id,
            title = response.title,
            cover = response.covers.map { mapCoverResponseToViewData(it) }
        )
    }

    private fun mapCoverResponseToViewData(coverResponse: CoverResponse): DetailsMovieViewData {
        return DetailsMovieViewData(
            id = coverResponse.id,
            imageUrl = coverResponse.imageUrl,
            name = coverResponse.name,
            description = coverResponse.description,
            cast = coverResponse.cast
        )
    }
}