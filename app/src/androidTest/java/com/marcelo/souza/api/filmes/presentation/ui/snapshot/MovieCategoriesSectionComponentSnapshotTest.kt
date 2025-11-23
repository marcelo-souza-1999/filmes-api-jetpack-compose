package com.marcelo.souza.api.filmes.presentation.ui.snapshot

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import com.karumi.shot.ScreenshotTest
import com.marcelo.souza.api.filmes.domain.model.CategoryViewData
import com.marcelo.souza.api.filmes.domain.model.DetailsMovieViewData
import com.marcelo.souza.api.filmes.presentation.components.MovieCategoriesSectionComponent
import com.marcelo.souza.api.filmes.presentation.theme.ApiMoviesTheme
import org.junit.Rule
import org.junit.Test

class MovieCategoriesSectionComponentSnapshotTest : ScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun sampleMovie(id: Int, title: String): DetailsMovieViewData = DetailsMovieViewData(
        id = id,
        name = title,
        imageUrl = "",
        description = "Descrição de teste.",
        cast = "Elenco de teste"
    )

    private val mockCategories = listOf(
        CategoryViewData(
            id = 1,
            title = "A Casa do Dragão",
            cover = listOf(
                sampleMovie(101, "Rhaenyra Targaryen"),
                sampleMovie(102, "Daemon Targaryen"),
                sampleMovie(103, "Corlys Velaryon"),
                sampleMovie(104, "Alicent Hightower"),
            )
        ),
        CategoryViewData(
            id = 2,
            title = "A Dança dos Dragões",
            cover = listOf(
                sampleMovie(201, "Syrax"),
                sampleMovie(202, "Caraxes"),
                sampleMovie(203, "Meleys"),
            )
        )
    )

    @Test
    fun movieCategoriesSection_light_theme_snapshot() {
        composeTestRule.setContent {
            ApiMoviesTheme(darkTheme = false) {
                MovieCategoriesSectionComponent(
                    categories = mockCategories,
                    contentPadding = PaddingValues(8.dp),
                    onMovieClick = {}
                )
            }
        }

        composeTestRule.waitForIdle()

        compareScreenshot(
            rule = composeTestRule,
            name = "movie_categories_section_light"
        )
    }

    @Test
    fun movieCategoriesSection_dark_theme_snapshot() {
        composeTestRule.setContent {
            ApiMoviesTheme(darkTheme = true) {
                MovieCategoriesSectionComponent(
                    categories = mockCategories,
                    contentPadding = PaddingValues(8.dp),
                    onMovieClick = {}
                )
            }
        }

        composeTestRule.waitForIdle()

        compareScreenshot(
            rule = composeTestRule,
            name = "movie_categories_section_dark"
        )
    }

    @Test
    fun movieCategoriesSection_empty_data_snapshot() {
        composeTestRule.setContent {
            ApiMoviesTheme(darkTheme = false) {
                MovieCategoriesSectionComponent(
                    categories = emptyList(),
                    contentPadding = PaddingValues(8.dp),
                    onMovieClick = {}
                )
            }
        }

        composeTestRule.waitForIdle()

        compareScreenshot(
            rule = composeTestRule,
            name = "movie_categories_section_empty"
        )
    }
}