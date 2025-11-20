package com.marcelo.souza.api.filmes.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    @SerialName("categoria")
    val categories: List<CategoryResponse>
)

@Serializable
data class CategoryResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("titulo")
    val title: String,
    @SerialName("capas")
    val covers: List<CoverResponse>
)

@Serializable
data class CoverResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("url_imagem")
    val imageUrl: String,
    @SerialName("nome")
    val name: String,
    @SerialName("descricao")
    val description: String,
    @SerialName("elenco")
    val cast: String
)