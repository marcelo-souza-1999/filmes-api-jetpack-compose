package com.marcelo.souza.api.filmes.domain.repository

import com.marcelo.souza.api.filmes.domain.model.CategoryViewData
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovies(): Flow<List<CategoryViewData>>
}