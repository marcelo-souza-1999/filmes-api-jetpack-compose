package com.marcelo.souza.api.filmes.data.api

import com.marcelo.souza.api.filmes.data.model.MoviesResponse

interface MoviesApi {
    suspend fun getMoviesResponse(): MoviesResponse
}