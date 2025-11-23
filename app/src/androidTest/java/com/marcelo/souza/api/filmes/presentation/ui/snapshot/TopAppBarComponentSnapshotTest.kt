package com.marcelo.souza.api.filmes.presentation.ui.snapshot

import androidx.compose.ui.test.junit4.createComposeRule
import com.karumi.shot.ScreenshotTest
import com.marcelo.souza.api.filmes.presentation.components.TopAppBarComponent
import com.marcelo.souza.api.filmes.presentation.theme.ApiMoviesTheme
import org.junit.Rule
import org.junit.Test

class TopAppBarComponentSnapshotTest : ScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val shortTitle = "Filmes em Cartaz"
    private val longTitle = "A Ascensão e Queda do Império Targaryen"

    @Test
    fun topAppBar_light_no_back_button_snapshot() {
        composeTestRule.setContent {
            ApiMoviesTheme(darkTheme = false) {
                TopAppBarComponent(
                    title = shortTitle,
                    showBackButton = false,
                    onBackClick = {}
                )
            }
        }

        compareScreenshot(
            rule = composeTestRule,
            name = "top_app_bar_light_no_back"
        )
    }

    @Test
    fun topAppBar_light_with_back_button_snapshot() {
        composeTestRule.setContent {
            ApiMoviesTheme(darkTheme = false) {
                TopAppBarComponent(
                    title = shortTitle,
                    showBackButton = true,
                    onBackClick = {}
                )
            }
        }

        compareScreenshot(
            rule = composeTestRule,
            name = "top_app_bar_light_with_back"
        )
    }

    @Test
    fun topAppBar_dark_with_back_button_snapshot() {
        composeTestRule.setContent {
            ApiMoviesTheme(darkTheme = true) {
                TopAppBarComponent(
                    title = shortTitle,
                    showBackButton = true,
                    onBackClick = {}
                )
            }
        }

        compareScreenshot(
            rule = composeTestRule,
            name = "top_app_bar_dark_with_back"
        )
    }

    @Test
    fun topAppBar_long_title_snapshot() {
        composeTestRule.setContent {
            ApiMoviesTheme(darkTheme = false) {
                TopAppBarComponent(
                    title = longTitle,
                    showBackButton = true,
                    onBackClick = {}
                )
            }
        }

        compareScreenshot(
            rule = composeTestRule,
            name = "top_app_bar_long_title"
        )
    }
}