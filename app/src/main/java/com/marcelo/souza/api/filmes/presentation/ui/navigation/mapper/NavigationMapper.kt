package com.marcelo.souza.api.filmes.presentation.ui.navigation.mapper

import com.marcelo.souza.api.filmes.domain.model.CategoryViewData
import com.marcelo.souza.api.filmes.domain.model.DetailsMovieViewData
import com.marcelo.souza.api.filmes.presentation.ui.navigation.model.CoverNavData
import com.marcelo.souza.api.filmes.presentation.ui.navigation.model.MovieDetailsDataKey

fun DetailsMovieViewData.toNavData(): CoverNavData = CoverNavData(
    id = this.id,
    imageUrl = this.imageUrl,
    name = this.name,
    description = this.description,
    cast = this.cast
)

fun CategoryViewData.toMovieDetailsKey(): MovieDetailsDataKey = MovieDetailsDataKey(
    id = this.id,
    title = this.title,
    cover = this.cover.map { coverItem ->
        coverItem.toNavData()
    }
)