package com.marcelo.souza.api.filmes.di.modules

import com.marcelo.souza.api.filmes.data.api.MoviesApi
import com.marcelo.souza.api.filmes.data.api.MoviesApiImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent

@Module
@Single
class KtorModule : KoinComponent {

    @Single
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Single
    fun provideHttpClient(json: Json): HttpClient {
        return HttpClient(CIO) {
            expectSuccess = true
            install(ContentNegotiation) {
                json(json)
            }
            install(HttpCache)
        }
    }

    @Single
    @Named("BASE_URL")
    fun provideBaseUrl(): String = BASE_URL

    @Single
    fun provideMoviesApi(
        httpClient: HttpClient, @Named("BASE_URL") baseUrl: String
    ): MoviesApi {
        return MoviesApiImpl(httpClient = httpClient, baseUrl = baseUrl)
    }

    private companion object {
        const val BASE_URL = "https://stackmobile.com.br"
    }
}