package com.marcelo.souza.api.filmes.di.modules

import com.marcelo.souza.api.filmes.data.repository.MoviesRepositoryImpl
import com.marcelo.souza.api.filmes.domain.datasource.MoviesDataSource
import com.marcelo.souza.api.filmes.domain.repository.MoviesRepository
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class RepositoryModule {
    @Single
    fun providesMoviesRepository(
        movieDataSource: MoviesDataSource
    ): MoviesRepository = MoviesRepositoryImpl(movieDataSource)
}