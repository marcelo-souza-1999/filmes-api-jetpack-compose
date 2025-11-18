package com.marcelo.souza.api.filmes.presentation.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marcelo.souza.api.filmes.R
import com.marcelo.souza.api.filmes.data.model.MoviesApiErrors
import com.marcelo.souza.api.filmes.domain.model.DetailsMovieViewData
import com.marcelo.souza.api.filmes.presentation.components.ErrorDialog
import com.marcelo.souza.api.filmes.presentation.components.MovieCategoriesSectionComponent
import com.marcelo.souza.api.filmes.presentation.components.MovieCategoriesSectionShimmer
import com.marcelo.souza.api.filmes.presentation.components.TopAppBarComponent
import com.marcelo.souza.api.filmes.presentation.theme.LocalDimens
import com.marcelo.souza.api.filmes.presentation.theme.White
import com.marcelo.souza.api.filmes.presentation.viewmodel.MoviesViewModel
import com.marcelo.souza.api.filmes.presentation.viewmodel.viewstate.ViewStates
import com.patrik.fancycomposedialogs.properties.DialogButtonProperties
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoviesScreen(
    onMovieClick: (DetailsMovieViewData) -> Unit,
    onClose: () -> Unit,
    viewModel: MoviesViewModel = koinViewModel()
) {
    val dimen = LocalDimens.current
    val viewStateGetMovies by viewModel.viewStateGetMovies.collectAsStateWithLifecycle()

    var showMoviesErrorDialog by remember { mutableStateOf(false) }

    LaunchedEffect(viewStateGetMovies) {
        showMoviesErrorDialog = viewStateGetMovies is ViewStates.Error
    }

    Scaffold(
        topBar = {
            TopAppBarComponent(
                title = stringResource(R.string.title_movies_top_app_bar),
                showBackButton = false
            )
        }
    ) { paddingValues ->
        when (val state = viewStateGetMovies) {
            is ViewStates.Loading -> {
                MovieCategoriesSectionShimmer(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                )
            }

            is ViewStates.Success -> {
                MovieCategoriesSectionComponent(
                    modifier = Modifier
                        .padding(horizontal = dimen.size8)
                        .padding(top = dimen.size8),
                    contentPadding = paddingValues,
                    categories = state.movies,
                    onMovieClick = onMovieClick
                )
            }

            is ViewStates.Error -> {
                val (titleRes, messageRes, isErrorServer) = when (state.error) {
                    is MoviesApiErrors.Network -> Triple(
                        R.string.title_show_movies_network_error_dialog,
                        R.string.message_show_movies_network_error_dialog,
                        false
                    )

                    is MoviesApiErrors.Server -> Triple(
                        R.string.title_show_movies_unexpected_error_dialog,
                        R.string.message_show_movies_unexpected_error_dialog,
                        true
                    )

                    is MoviesApiErrors.Error -> Triple(
                        R.string.title_show_movies_unexpected_error_dialog,
                        R.string.message_show_movies_unexpected_error_dialog,
                        false
                    )
                }

                HandleErrorMoviesDialog(
                    title = stringResource(titleRes),
                    message = stringResource(messageRes),
                    isShowErrorMoviesDialogVisible = showMoviesErrorDialog,
                    isErrorServer = isErrorServer,
                    onMoviesErrorDismiss = { onClose() },
                    onRetryMovies = {
                        viewModel.retryMovies()
                    }
                )
            }
        }
    }
}

@Composable
private fun dialogPropsFor(isErrorServer: Boolean): DialogButtonProperties {
    val baseColor = MaterialTheme.colorScheme.primary
    return if (isErrorServer) {
        DialogButtonProperties(
            neutralButtonText = R.string.txt_btn_neutral_error_server_dialog,
            buttonColor = baseColor,
            buttonTextColor = White
        )
    } else {
        DialogButtonProperties(
            positiveButtonText = R.string.txt_btn_positive_try_again_error_dialog,
            negativeButtonText = R.string.txt_btn_negative_cancel_dialog,
            buttonColor = baseColor,
            buttonTextColor = White
        )
    }
}

@Composable
private fun HandleErrorMoviesDialog(
    title: String,
    message: String,
    isShowErrorMoviesDialogVisible: Boolean,
    isErrorServer: Boolean,
    onMoviesErrorDismiss: () -> Unit,
    onRetryMovies: () -> Unit
) {
    if (!isShowErrorMoviesDialogVisible) return

    val dialogProps = dialogPropsFor(isErrorServer)

    ErrorDialog(
        title = title,
        message = message,
        isCancelable = true,
        isErrorServer = isErrorServer,
        dialogButtonProperties = dialogProps,
        onConfirmClick = { onRetryMovies() },
        onDismissClick = { onMoviesErrorDismiss() }
    )
}