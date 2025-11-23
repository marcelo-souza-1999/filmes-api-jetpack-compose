package com.marcelo.souza.api.filmes.presentation.ui.activity

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.marcelo.souza.api.filmes.domain.model.CategoryViewData
import com.marcelo.souza.api.filmes.domain.model.DetailsMovieViewData
import com.marcelo.souza.api.filmes.domain.repository.MoviesRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val fakeRepository = mockk<MoviesRepository>(relaxed = true)

    private val sampleMovie = DetailsMovieViewData(
        id = 1,
        name = "House of the Dragon",
        imageUrl = "https://example.com/dragon.jpg",
        description = "Descrição teste",
        cast = "Emma D'Arcy, Matt Smith"
    )

    private val sampleCategory = CategoryViewData(
        id = 10,
        title = "Ação",
        cover = listOf(sampleMovie)
    )

    @Before
    fun setUp() {
        every { fakeRepository.getMovies() } returns flowOf(listOf(sampleCategory))

        val testModule = module {
            single<MoviesRepository> { fakeRepository }
        }

        if (GlobalContext.getOrNull() != null) stopKoin()
        startKoin { modules(testModule) }
    }

    @Test
    fun startActivity_showsMoviesList_and_navigatesToDetails_onCardClick_and_back() {
        composeRule.waitForIdle()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithTag("cardMovie")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onNodeWithTag("topAppBarComponent").assertIsDisplayed()

        composeRule.onAllNodesWithTag("cardMovie")[0].performClick()
        composeRule.waitForIdle()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            try {
                composeRule.onNodeWithTag("movieTitle").assertExists()
                true
            } catch (_: AssertionError) {
                false
            }
        }

        composeRule.onNodeWithTag("movieTitle").assertIsDisplayed()

        composeRule.onNodeWithTag("topAppBarBackButton").performClick()
        composeRule.waitForIdle()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule.onAllNodesWithTag("cardMovie")
                .fetchSemanticsNodes().isNotEmpty()
        }

        composeRule.onAllNodesWithTag("cardMovie")[0].assertIsDisplayed()
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}
