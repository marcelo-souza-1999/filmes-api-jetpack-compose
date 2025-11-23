package com.marcelo.souza.api.filmes.presentation.viewmodel

import com.marcelo.souza.api.filmes.data.model.MoviesApiErrors
import com.marcelo.souza.api.filmes.domain.model.CategoryViewData
import com.marcelo.souza.api.filmes.domain.model.DetailsMovieViewData
import com.marcelo.souza.api.filmes.domain.repository.MoviesRepository
import com.marcelo.souza.api.filmes.presentation.viewmodel.viewstate.ViewStates
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class MoviesViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    lateinit var repository: MoviesRepository

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
    fun `when repository emits data viewState becomes Success`() = runTest {
        val sample = sampleCategoryList()
        coEvery { repository.getMovies() } returns flowOf(sample)

        val viewModel = MoviesViewModel(repository)

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.viewStateGetMovies.value
        assertTrue(state is ViewStates.Success)
        val success = state as ViewStates.Success
        assertEquals(sample, success.movies)

        coVerify(exactly = 1) { repository.getMovies() }
    }

    @Test
    fun `when repository throws Network error viewState becomes Error with Network`() = runTest {
        val ioException = IOException("no network")
        coEvery { repository.getMovies() } returns flow { throw MoviesApiErrors.Network(ioException) }

        val viewModel = MoviesViewModel(repository)

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.viewStateGetMovies.value
        assertTrue(state is ViewStates.Error)
        val errorState = state as ViewStates.Error
        assertTrue(errorState.error is MoviesApiErrors.Network)

        coVerify(exactly = 1) { repository.getMovies() }
    }

    @Test
    fun `when repository throws Server error viewState becomes Error with Server`() = runTest {
        val serverError = MoviesApiErrors.Server(statusCode = 500, body = "boom")
        coEvery { repository.getMovies() } returns flow { throw serverError }

        val viewModel = MoviesViewModel(repository)

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.viewStateGetMovies.value
        assertTrue(state is ViewStates.Error)
        val errorState = state as ViewStates.Error
        assertTrue(errorState.error is MoviesApiErrors.Server)
        assertEquals(500, (errorState.error as MoviesApiErrors.Server).statusCode)

        coVerify(exactly = 1) { repository.getMovies() }
    }

    @Test
    fun `when repository throws generic throwable viewState becomes Error with Error`() = runTest {
        val generic = RuntimeException("boom")
        coEvery { repository.getMovies() } returns flow { throw generic }

        val viewModel = MoviesViewModel(repository)

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.viewStateGetMovies.value
        assertTrue(state is ViewStates.Error)
        val errorState = state as ViewStates.Error
        assertTrue(errorState.error is MoviesApiErrors.Error)

        coVerify(exactly = 1) { repository.getMovies() }
    }

    @Test
    fun `retryMovies calls repository again and updates state`() = runTest {
        val sample = sampleCategoryList()

        coEvery { repository.getMovies() } returnsMany listOf(
            flow { throw MoviesApiErrors.Network(IOException("no net")) },
            flowOf(sample)
        )

        val viewModel = MoviesViewModel(repository)

        testDispatcher.scheduler.advanceUntilIdle()
        val stateAfterFirst = viewModel.viewStateGetMovies.value
        assertTrue(stateAfterFirst is ViewStates.Error)

        viewModel.retryMovies()

        testDispatcher.scheduler.advanceUntilIdle()

        val finalState = viewModel.viewStateGetMovies.value
        assertTrue(finalState is ViewStates.Success)
        val success = finalState as ViewStates.Success
        assertEquals(sample, success.movies)

        coVerify(exactly = 2) { repository.getMovies() }
    }
}