package com.marcelo.souza.api.filmes.data.repository

import com.marcelo.souza.api.filmes.domain.datasource.MoviesDataSource
import com.marcelo.souza.api.filmes.domain.repository.MoviesRepository
import org.koin.core.annotation.Single

@Single
class MoviesRepositoryImpl(
    private val dataSource: MoviesDataSource
) : MoviesRepository {
    override fun getMovies() = dataSource.fetchMovies()
}