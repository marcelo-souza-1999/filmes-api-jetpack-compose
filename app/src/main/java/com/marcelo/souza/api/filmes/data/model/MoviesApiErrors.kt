package com.marcelo.souza.api.filmes.data.model

import java.io.IOException

sealed class MoviesApiErrors(cause: Throwable? = null) : Exception(cause) {

    class Network(val ioException: IOException) :
        MoviesApiErrors(ioException)

    class Server(
        val statusCode: Int,
        val body: String? = null
    ) : MoviesApiErrors()

    class Error(val originalThrowable: Throwable) :
        MoviesApiErrors(originalThrowable)
}