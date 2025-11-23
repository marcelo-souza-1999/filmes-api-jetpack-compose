package com.marcelo.souza.api.filmes.data.datasource

import com.marcelo.souza.api.filmes.data.api.MoviesApi
import com.marcelo.souza.api.filmes.data.model.CategoryResponse
import com.marcelo.souza.api.filmes.data.model.CoverResponse
import com.marcelo.souza.api.filmes.data.model.MoviesApiErrors
import com.marcelo.souza.api.filmes.data.model.MoviesResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesDataSourceImplTest {

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    lateinit var moviesApi: MoviesApi

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun sampleApiResponse(): MoviesResponse {
        val cover1 = CoverResponse(
            id = 7,
            imageUrl = "https://example.com/filme.jpg",
            name = "Hanna",
            description = "Descrição Hanna",
            cast = "Elenco Hanna"
        )
        val category = CategoryResponse(
            id = 1,
            title = "Ação",
            covers = listOf(cover1)
        )
        return MoviesResponse(categories = listOf(category))
    }

    @Test
    fun `fetchMovies emits mapped CategoryViewData when api returns success`() = runTest {
        val apiResponse = sampleApiResponse()
        coEvery { moviesApi.getMoviesResponse() } returns apiResponse

        val dataSource = MoviesDataSourceImpl(moviesApi)

        val emitted = dataSource.fetchMovies().first()

        assertEquals(1, emitted.size)
        val categoryView = emitted[0]
        assertEquals(1, categoryView.id)
        assertEquals("Ação", categoryView.title)
        assertEquals(1, categoryView.cover.size)

        val detail = categoryView.cover[0]
        assertEquals(7, detail.id)
        assertEquals("Hanna", detail.name)
        assertEquals("https://example.com/filme.jpg", detail.imageUrl)
        assertEquals("Descrição Hanna", detail.description)
        assertEquals("Elenco Hanna", detail.cast)
    }

    @Test
    fun `fetchMovies rethrows Network error from api`() = runTest {
        val ioEx = IOException("no network")
        coEvery { moviesApi.getMoviesResponse() } throws MoviesApiErrors.Network(ioEx)

        val dataSource = MoviesDataSourceImpl(moviesApi)

        try {
            dataSource.fetchMovies().first()
            throw AssertionError("Esperava exceção do tipo MoviesApiErrors.Network")
        } catch (t: Throwable) {
            assert(t is MoviesApiErrors.Network)
            val network = t as MoviesApiErrors.Network
            assertEquals(ioEx.message, network.ioException.message)
        }
    }

    @Test
    fun `fetchMovies rethrows Server error from api`() = runTest {
        val serverError = MoviesApiErrors.Server(statusCode = 500, body = "boom")
        coEvery { moviesApi.getMoviesResponse() } throws serverError

        val dataSource = MoviesDataSourceImpl(moviesApi)

        try {
            dataSource.fetchMovies().first()
            throw AssertionError("Esperava exceção do tipo MoviesApiErrors.Server")
        } catch (t: Throwable) {
            assert(t is MoviesApiErrors.Server)
            val server = t as MoviesApiErrors.Server
            assertEquals(500, server.statusCode)
            assertEquals("boom", server.body)
        }
    }

    @Test
    fun `fetchMovies rethrows generic Error from api`() = runTest {
        val generic = RuntimeException("boom")
        coEvery { moviesApi.getMoviesResponse() } throws generic

        val dataSource = MoviesDataSourceImpl(moviesApi)

        try {
            dataSource.fetchMovies().first()
            throw AssertionError("Esperava exceção do tipo MoviesApiErrors.Error")
        } catch (t: Throwable) {
            assert(t is RuntimeException)
            assertEquals("boom", t.message)
        }
    }
}