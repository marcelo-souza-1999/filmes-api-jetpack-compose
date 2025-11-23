package com.marcelo.souza.api.filmes.presentation.ui.screen

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.marcelo.souza.api.filmes.R
import com.marcelo.souza.api.filmes.domain.model.DetailsMovieViewData
import com.marcelo.souza.api.filmes.presentation.theme.ApiMoviesTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    private val sampleMovie = DetailsMovieViewData(
        id = 1,
        name = "House of the Dragon",
        imageUrl = "https://example.com/dragon.jpg",
        description = "A luta pelo Trono de Ferro começa muito antes de Game of Thrones.",
        cast = "Emma D'Arcy, Matt Smith"
    )

    @Test
    fun movieDetailsShowsImageTitleDescriptionAndCast() {
        composeTestRule.setContent {
            ApiMoviesTheme {
                MovieDetailsScreen(movie = sampleMovie, onBackClick = {})
            }
        }

        composeTestRule.onNodeWithTag("topAppBarComponent").assertIsDisplayed()
        composeTestRule.onNodeWithTag("movieTitle").assertIsDisplayed()
        composeTestRule.onNodeWithText(sampleMovie.description).assertIsDisplayed()

        val castLabel = context.getString(R.string.title_movies_details_cast)
        composeTestRule.onNodeWithText(castLabel + " " + sampleMovie.cast).assertIsDisplayed()
    }

    @Test
    fun backButtonCallsOnBackClick() {
        var clicked = false

        composeTestRule.setContent {
            ApiMoviesTheme {
                MovieDetailsScreen(
                    movie = sampleMovie,
                    onBackClick = { clicked = true }
                )
            }
        }

        composeTestRule
            .onNodeWithTag("topAppBarBackButton")
            .performClick()

        composeTestRule.waitForIdle()

        assert(clicked)
    }
}