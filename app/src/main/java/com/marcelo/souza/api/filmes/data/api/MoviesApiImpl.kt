package com.marcelo.souza.api.filmes.data.api

import com.marcelo.souza.api.filmes.data.model.MoviesApiErrors
import com.marcelo.souza.api.filmes.data.model.MoviesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import org.koin.core.annotation.Single
import java.io.IOException
import java.nio.channels.UnresolvedAddressException

class MoviesApiImpl(
    private val httpClient: HttpClient,
    private val baseUrl: String
) : MoviesApi {

    override suspend fun getMoviesResponse(): MoviesResponse {
        val url = "$baseUrl/$ROUTE_MOVIES"
        try {
            val response: HttpResponse = httpClient.get(url)
            return response.body()
        } catch (e: CancellationException) {
            throw e
        } catch (e: ResponseException) {
            throw e.toMoviesApiError()
        } catch (e: Throwable) {
            throw mapToMoviesApiError(e)
        }
    }

    private fun mapToMoviesApiError(exception: Throwable): MoviesApiErrors {
        return when (exception) {
            is MoviesApiErrors -> exception
            is IOException -> MoviesApiErrors.Network(ioException = exception)
            is UnresolvedAddressException -> {
                MoviesApiErrors.Network(ioException = IOException(exception))
            }
            is SerializationException -> MoviesApiErrors.Error(originalThrowable = exception)

            else -> MoviesApiErrors.Error(originalThrowable = exception)
        }
    }

    private suspend fun ResponseException.toMoviesApiError(): MoviesApiErrors.Server {
        val bodyText = runCatching { response.bodyAsText() }.getOrNull()

        return MoviesApiErrors.Server(
            statusCode = this.response.status.value,
            body = bodyText
        )
    }

    private companion object {
        const val ROUTE_MOVIES = "filmes"
    }
}