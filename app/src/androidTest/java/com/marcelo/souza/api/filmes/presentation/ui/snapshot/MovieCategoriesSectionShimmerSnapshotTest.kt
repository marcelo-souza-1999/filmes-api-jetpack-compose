package com.marcelo.souza.api.filmes.presentation.ui.snapshot

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import com.karumi.shot.ScreenshotTest
import com.marcelo.souza.api.filmes.presentation.components.MovieCategoriesSectionShimmer
import com.marcelo.souza.api.filmes.presentation.theme.ApiMoviesTheme
import org.junit.Rule
import org.junit.Test

class MovieCategoriesSectionShimmerSnapshotTest : ScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun movieCategoriesSectionShimmer_light_theme_snapshot() {
        composeTestRule.setContent {
            ApiMoviesTheme(darkTheme = false) {
                MovieCategoriesSectionShimmer(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                )
            }
        }

        composeTestRule.waitForIdle()

        compareScreenshot(
            rule = composeTestRule,
            name = "movie_categories_shimmer_light"
        )
    }

    @Test
    fun movieCategoriesSectionShimmer_dark_theme_snapshot() {
        composeTestRule.setContent {
            ApiMoviesTheme(darkTheme = true) {
                MovieCategoriesSectionShimmer(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                )
            }
        }

        composeTestRule.waitForIdle()

        compareScreenshot(
            rule = composeTestRule,
            name = "movie_categories_shimmer_dark"
        )
    }
}