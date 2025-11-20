package com.marcelo.souza.api.filmes.presentation.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.marcelo.souza.api.filmes.domain.model.DetailsMovieViewData
import com.marcelo.souza.api.filmes.presentation.theme.ApiMoviesTheme
import com.marcelo.souza.api.filmes.presentation.ui.navigation.mapper.toNavData
import com.marcelo.souza.api.filmes.presentation.ui.navigation.model.MovieDetailsDataKey
import com.marcelo.souza.api.filmes.presentation.ui.navigation.model.MoviesKey
import com.marcelo.souza.api.filmes.presentation.ui.screen.MovieDetailsScreen
import com.marcelo.souza.api.filmes.presentation.ui.screen.MoviesScreen
import com.marcelo.souza.api.filmes.presentation.utils.Constants.DURATION_MS

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ApiMoviesTheme {
                StartNavigation(onFinish = { finish() })
            }
        }
    }
}

@Composable
fun StartNavigation(onFinish: () -> Unit) {
    val backStack = rememberNavBackStack(MoviesKey)

    NavDisplay(
        backStack = backStack,
        transitionSpec = {
            ContentTransform(
                targetContentEnter = fadeIn(tween(durationMillis = DURATION_MS)) + slideInHorizontally(
                    animationSpec = tween(durationMillis = DURATION_MS),
                    initialOffsetX = { fullWidth -> fullWidth }
                ),

                initialContentExit = fadeOut(tween(durationMillis = DURATION_MS)) + slideOutHorizontally(
                    animationSpec = tween(durationMillis = DURATION_MS),
                    targetOffsetX = { fullWidth -> -fullWidth }
                )
            )
        },
        entryProvider = entryProvider {
            entry<MoviesKey> {
                MoviesScreen(
                    onMovieClick = { movie: DetailsMovieViewData ->
                        val coverNav = movie.toNavData()
                        val navKey = MovieDetailsDataKey(
                            id = movie.id,
                            title = movie.name,
                            cover = listOf(coverNav)
                        )
                        backStack.add(navKey)
                    },
                    onClose = { onFinish() }
                )
            }

            entry<MovieDetailsDataKey> { key ->
                val cover = key.cover.firstOrNull()
                if (cover != null) {
                    val details = DetailsMovieViewData(
                        id = cover.id,
                        name = cover.name,
                        imageUrl = cover.imageUrl,
                        description = cover.description,
                        cast = cover.cast
                    )
                    MovieDetailsScreen(
                        movie = details,
                        onBackClick = { backStack.removeLastOrNull() })
                } else {
                    backStack.removeLastOrNull()
                }
            }
        },
        onBack = {
            if (backStack.size > 1) {
                backStack.removeLastOrNull()
            } else {
                onFinish()
            }
        }
    )
}