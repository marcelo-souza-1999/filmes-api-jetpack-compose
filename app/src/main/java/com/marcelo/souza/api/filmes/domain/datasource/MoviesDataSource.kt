package com.marcelo.souza.api.filmes.domain.datasource

import com.marcelo.souza.api.filmes.domain.model.CategoryViewData
import kotlinx.coroutines.flow.Flow

interface MoviesDataSource {
    fun fetchMovies(): Flow<List<CategoryViewData>>
}