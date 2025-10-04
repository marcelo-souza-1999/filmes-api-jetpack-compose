package com.marcelo.souza.api.filmes.presentation.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.marcelo.souza.api.filmes.R
import com.marcelo.souza.api.filmes.presentation.components.MovieCategoriesSectionComponent
import com.marcelo.souza.api.filmes.presentation.components.TopAppBarComponent
import com.marcelo.souza.api.filmes.presentation.theme.ApiMoviesTheme

@Composable
fun MoviesScreen(

) {
    Scaffold(
        topBar = {
            TopAppBarComponent(
                title = stringResource(R.string.title_movies_top_app_bar),
                showBackButton = false
            )
        }
    ) { paddingValues ->
        MovieCategoriesSectionComponent(
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.size_8))
                .padding(top = dimensionResource(R.dimen.size_8)),
            contentPadding = paddingValues
        )
    }
}

@Preview
@Composable
fun MoviesScreenPreview() {
    ApiMoviesTheme(darkTheme = true) {
        MoviesScreen()
    }
}