package com.marcelo.souza.api.filmes.domain.model

data class CategoryViewData(
    val id: Int,
    val title: String,
    val cover: List<DetailsMovieViewData>
)

data class DetailsMovieViewData(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val description: String,
    val cast: String
)