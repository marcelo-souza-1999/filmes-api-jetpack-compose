package com.marcelo.souza.api.filmes.data.repository

import com.marcelo.souza.api.filmes.data.model.MoviesApiErrors
import com.marcelo.souza.api.filmes.domain.datasource.MoviesDataSource
import com.marcelo.souza.api.filmes.domain.model.CategoryViewData
import com.marcelo.souza.api.filmes.domain.model.DetailsMovieViewData
import com.marcelo.souza.api.filmes.domain.repository.MoviesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesRepositoryImplTest {

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    lateinit var dataSource: MoviesDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun sampleCategoryList(): List<CategoryViewData> {
        val details = DetailsMovieViewData(
            id = 1,
            name = "Titulo",
            imageUrl = "https://example.com/img.jpg",
            description = "desc",
            cast = "cast"
        )
        return listOf(
            CategoryViewData(
                id = 10,
                title = "Categoria X",
                cover = listOf(details)
            )
        )
    }

    @Test
    fun `getMovies delegates to dataSource and returns emitted list`() = runTest {
        val expected = sampleCategoryList()
        coEvery { dataSource.fetchMovies() } returns flowOf(expected)

        val repository: MoviesRepository = MoviesRepositoryImpl(dataSource)

        val actual = repository.getMovies().first()

        assertEquals(expected, actual)
        coVerify(exactly = 1) { dataSource.fetchMovies() }
    }

    @Test
    fun `getMovies propagates Network error from dataSource`() = runTest {
        val ioException = IOException("no network")
        coEvery { dataSource.fetchMovies() } returns flow { throw MoviesApiErrors.Network(ioException) }

        val repository: MoviesRepository = MoviesRepositoryImpl(dataSource)

        var thrownException: Throwable? = null
        try {
            repository.getMovies().collect { /* consume */ }
        } catch (caught: Throwable) {
            thrownException = caught
        }

        assertTrue(thrownException is MoviesApiErrors.Network)
        coVerify(exactly = 1) { dataSource.fetchMovies() }
    }

    @Test
    fun `getMovies propagates Server error from dataSource`() = runTest {
        val serverError = MoviesApiErrors.Server(statusCode = 500, body = "boom")
        coEvery { dataSource.fetchMovies() } returns flow { throw serverError }

        val repository: MoviesRepository = MoviesRepositoryImpl(dataSource)

        var thrownException: Throwable? = null
        try {
            repository.getMovies().collect { /* consume */ }
        } catch (caught: Throwable) {
            thrownException = caught
        }

        assertTrue(thrownException is MoviesApiErrors.Server)
        assertEquals(500, (thrownException as MoviesApiErrors.Server).statusCode)
        coVerify(exactly = 1) { dataSource.fetchMovies() }
    }
}
