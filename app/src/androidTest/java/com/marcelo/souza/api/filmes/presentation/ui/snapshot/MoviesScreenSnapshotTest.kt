package com.marcelo.souza.api.filmes.presentation.ui.snapshot

import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.karumi.shot.ScreenshotTest
import com.marcelo.souza.api.filmes.data.model.MoviesApiErrors
import com.marcelo.souza.api.filmes.domain.model.CategoryViewData
import com.marcelo.souza.api.filmes.domain.model.DetailsMovieViewData
import com.marcelo.souza.api.filmes.presentation.theme.ApiMoviesTheme
import com.marcelo.souza.api.filmes.presentation.ui.screen.MoviesScreen
import com.marcelo.souza.api.filmes.presentation.viewmodel.MoviesViewModel
import com.marcelo.souza.api.filmes.presentation.viewmodel.viewstate.ViewStates
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class MoviesScreenSnapshotTest : ScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun sampleMovie(
        id: Int = 1,
        name: String = "House of the Dragon"
    ): DetailsMovieViewData =
        DetailsMovieViewData(
            id = id,
            name = name,
            imageUrl = "https://example.com/image.jpg",
            description = "Descrição teste do filme.",
            cast = "Ator 1, Ator 2"
        )

    private fun sampleCategory(id: Int, title: String): CategoryViewData = CategoryViewData(
        id = id,
        title = title,
        cover = listOf(
            sampleMovie(1, "Filme A"),
            sampleMovie(2, "Filme B"),
            sampleMovie(3, "Filme C")
        )
    )

    private fun createMockViewModel(initialState: ViewStates): MoviesViewModel {
        val vm = mockk<MoviesViewModel>(relaxed = true)
        val stateFlow = MutableStateFlow(initialState)
        every { vm.viewStateGetMovies } returns stateFlow
        return vm
    }

    @Test
    fun moviesScreen_loading_snapshot() {
        val vm = createMockViewModel(ViewStates.Loading)

        composeTestRule.setContent {
            ApiMoviesTheme {
                MoviesScreen(onMovieClick = {}, onClose = {}, viewModel = vm)
            }
        }

        composeTestRule.waitForIdle()

        compareScreenshot(composeTestRule, name = "movies_screen_loading")
    }

    @Test
    fun moviesScreen_success_snapshot() {
        val categories = listOf(
            sampleCategory(id = 10, title = "Ação"),
            sampleCategory(id = 11, title = "Comédia"),
            sampleCategory(id = 12, title = "Drama")
        )

        val vm = createMockViewModel(ViewStates.Success(categories))

        composeTestRule.setContent {
            ApiMoviesTheme {
                MoviesScreen(onMovieClick = {}, onClose = {}, viewModel = vm)
            }
        }

        composeTestRule.onNodeWithText("Ação", ignoreCase = true).assertExists()
        composeTestRule.waitForIdle()

        compareScreenshot(composeTestRule, name = "movies_screen_success")
    }

    @Test
    fun moviesScreen_error_server_snapshot() {
        val vm = createMockViewModel(
            ViewStates.Error(
                MoviesApiErrors.Server(
                    500,
                    "Internal Server Error"
                )
            )
        )

        composeTestRule.setContent {
            ApiMoviesTheme {
                MoviesScreen(onMovieClick = {}, onClose = {}, viewModel = vm)
            }
        }

        composeTestRule.waitForIdle()

        compareScreenshot(
            node = composeTestRule.onNode(isDialog()),
            name = "movies_screen_error_server"
        )
    }

    @Test
    fun moviesScreen_error_network_snapshot() {
        val vm =
            createMockViewModel(ViewStates.Error(MoviesApiErrors.Network(IOException("No Internet"))))

        composeTestRule.setContent {
            ApiMoviesTheme {
                MoviesScreen(onMovieClick = {}, onClose = {}, viewModel = vm)
            }
        }

        composeTestRule.waitForIdle()

        compareScreenshot(
            node = composeTestRule.onNode(isDialog()),
            name = "movies_screen_error_network"
        )
    }

    @Test
    fun moviesScreen_error_generic_snapshot() {
        val vm = createMockViewModel(
            ViewStates.Error(
                MoviesApiErrors.Error(
                    Throwable("Erro Desconhecido")
                )
            )
        )

        composeTestRule.setContent {
            ApiMoviesTheme {
                MoviesScreen(onMovieClick = {}, onClose = {}, viewModel = vm)
            }
        }

        composeTestRule.waitForIdle()

        compareScreenshot(
            node = composeTestRule.onNode(isDialog()),
            name = "movies_screen_error_generic"
        )
    }
}