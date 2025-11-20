package com.marcelo.souza.api.filmes.data.datasource

import com.marcelo.souza.api.filmes.data.api.MoviesApi
import com.marcelo.souza.api.filmes.data.mapper.MoviesMapper.mapCategoryResponseToViewData
import com.marcelo.souza.api.filmes.domain.datasource.MoviesDataSource
import com.marcelo.souza.api.filmes.domain.model.CategoryViewData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single

@Single
class MoviesDataSourceImpl(
    private val moviesApi: MoviesApi
) : MoviesDataSource {
    override fun fetchMovies(): Flow<List<CategoryViewData>> = flow {
        val response = moviesApi.getMoviesResponse()
        emit(response.categories.map { category ->
            mapCategoryResponseToViewData(response = category)
        })
    }
}