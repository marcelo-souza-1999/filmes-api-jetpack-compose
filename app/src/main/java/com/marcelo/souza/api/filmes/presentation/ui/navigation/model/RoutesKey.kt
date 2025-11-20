package com.marcelo.souza.api.filmes.presentation.ui.navigation.model

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface RoutesKey : NavKey

@Serializable
object MoviesKey : RoutesKey

@Serializable
data class CoverNavData(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val description: String,
    val cast: String
)

@Serializable
data class MovieDetailsDataKey(
    val id: Int,
    val title: String,
    val cover: List<CoverNavData>
) : RoutesKey