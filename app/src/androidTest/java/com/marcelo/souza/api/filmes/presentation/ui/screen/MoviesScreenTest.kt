package com.marcelo.souza.api.filmes.presentation.ui.screen

import android.content.Context
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.marcelo.souza.api.filmes.R
import com.marcelo.souza.api.filmes.data.model.MoviesApiErrors
import com.marcelo.souza.api.filmes.domain.model.CategoryViewData
import com.marcelo.souza.api.filmes.domain.model.DetailsMovieViewData
import com.marcelo.souza.api.filmes.presentation.theme.ApiMoviesTheme
import com.marcelo.souza.api.filmes.presentation.viewmodel.MoviesViewModel
import com.marcelo.souza.api.filmes.presentation.viewmodel.viewstate.ViewStates
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MoviesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    private lateinit var stateFlow: MutableStateFlow<ViewStates>
    private val mockViewModel = mockk<MoviesViewModel>(relaxed = true)

    @Before
    fun setup() {
        stateFlow = MutableStateFlow(ViewStates.Loading)
        every { mockViewModel.viewStateGetMovies } returns stateFlow
        every { mockViewModel.retryMovies() } returns Unit
    }

    private fun sampleCategories(): List<CategoryViewData> {
        val d1 = DetailsMovieViewData(
            id = 1,
            name = "Hanna",
            imageUrl = "https://example.com/hanna.jpg",
            description = "desc hanna",
            cast = "actor a"
        )
        val d2 = DetailsMovieViewData(
            id = 2,
            name = "Gemini",
            imageUrl = "https://example.com/gemini.jpg",
            description = "desc gemini",
            cast = "actor b"
        )

        return listOf(
            CategoryViewData(id = 10, title = "Ação", cover = listOf(d1, d2)),
            CategoryViewData(id = 11, title = "Aventura", cover = listOf(d1))
        )
    }

    private fun setContent(
        onMovieClick: (DetailsMovieViewData) -> Unit = {},
        onClose: () -> Unit = {}
    ) {
        composeTestRule.setContent {
            ApiMoviesTheme {
                MoviesScreen(
                    onMovieClick = onMovieClick,
                    onClose = onClose,
                    viewModel = mockViewModel
                )
            }
        }
    }

    @Test
    fun whenSuccessDisplaysAllMovieCards() {
        val categories = sampleCategories()
        stateFlow.value = ViewStates.Success(categories)

        setContent()

        composeTestRule.onAllNodesWithTag("cardMovie").assertCountEquals(3)
    }

    @Test
    fun whenClickOnCardCallsOnMovieClickWithDetails() {
        val categories = sampleCategories()
        stateFlow.value = ViewStates.Success(categories)

        var clicked: DetailsMovieViewData? = null
        setContent(onMovieClick = { clicked = it })

        val first = composeTestRule.onAllNodesWithTag("cardMovie")[0]
        first.performClick()

        composeTestRule.waitForIdle()

        assert(clicked != null)
        assert(clicked?.id == 1 || clicked?.name != null)
    }

    @Test
    fun whenNetworkErrorShowsDialogAndRetryCallsViewmodelRetryMovies() {
        stateFlow.value = ViewStates.Error(MoviesApiErrors.Network(IOException("no net")))

        setContent()

        val msg = context.getString(R.string.message_show_movies_network_error_dialog)
        composeTestRule.onNodeWithText(msg).assertIsDisplayed()

        val retryText = context.getString(R.string.txt_btn_positive_try_again_error_dialog)
        composeTestRule.onNodeWithText(retryText).performClick()

        verify { mockViewModel.retryMovies() }
    }

    @Test
    fun whenServerErrorShowsNeutralDialogAndOnCloseIsCalled() {
        stateFlow.value = ViewStates.Error(MoviesApiErrors.Server(statusCode = 500, body = "boom"))

        var closed = false
        setContent(onClose = { closed = true })

        val neutral = context.getString(R.string.txt_btn_neutral_error_server_dialog)
        composeTestRule.onNodeWithText(neutral).assertIsDisplayed()
        composeTestRule.onNodeWithText(neutral).performClick()

        composeTestRule.waitForIdle()
        assert(closed)
    }
}