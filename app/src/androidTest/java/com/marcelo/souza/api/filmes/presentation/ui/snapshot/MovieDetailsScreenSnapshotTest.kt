package com.marcelo.souza.api.filmes.presentation.ui.snapshot

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.karumi.shot.ScreenshotTest
import com.marcelo.souza.api.filmes.domain.model.DetailsMovieViewData
import com.marcelo.souza.api.filmes.presentation.theme.ApiMoviesTheme
import com.marcelo.souza.api.filmes.presentation.ui.screen.MovieDetailsScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDetailsScreenSnapshotTest : ScreenshotTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val fakeMovieNormal = DetailsMovieViewData(
        id = 1,
        name = "House of the Dragon",
        imageUrl = "",
        description = "Uma história marcada por fogo, sangue e ambição. " +
                "Os Sete Reinos jamais viram tamanha disputa pelo Trono de Ferro.",
        cast = "Emma D'Arcy, Matt Smith"
    )

    private val fakeMovieLong = fakeMovieNormal.copy(
        name = "A Queda da Casa: Versão Estendida",
        description = "Uma história marcada por fogo, sangue e ambição. " +
                "Os Sete Reinos jamais viram tamanha disputa pelo Trono de Ferro. " +
                "Esta é uma descrição muito, muito, muito longa. " +
                "Ela se estende por várias linhas, preenchendo o espaço da tela e forçando o scroll " +
                "para baixo. " +
                "Assim, garantimos que o teste de rolagem é relevante e que todos os elementos da " +
                "tela serão visíveis " +
                "para o teste de snapshot, mesmo que seja preciso rolar para baixo."
    )

    @Test
    fun movieDetails_snapshot_light_theme() {
        composeRule.setContent {
            ApiMoviesTheme(darkTheme = false) {
                MovieDetailsScreen(
                    movie = fakeMovieNormal,
                    onBackClick = {}
                )
            }
        }
        composeRule.waitForIdle()
        compareScreenshot(
            rule = composeRule,
            name = "movie_details_screen_light"
        )
    }

    @Test
    fun movieDetails_snapshot_dark_theme() {
        composeRule.setContent {
            ApiMoviesTheme(darkTheme = true) {
                MovieDetailsScreen(
                    movie = fakeMovieNormal,
                    onBackClick = {}
                )
            }
        }
        composeRule.waitForIdle()
        compareScreenshot(
            rule = composeRule,
            name = "movie_details_screen_dark"
        )
    }

    @Test
    fun movieDetails_snapshot_scroll() {
        composeRule.setContent {
            ApiMoviesTheme(darkTheme = false) {
                MovieDetailsScreen(
                    movie = fakeMovieLong,
                    onBackClick = {}
                )
            }
        }

        composeRule.waitForIdle()

        composeRule.onNodeWithTag("movieTitle").performScrollTo()

        compareScreenshot(
            rule = composeRule,
            name = "movie_details_screen_scroll"
        )
    }
}