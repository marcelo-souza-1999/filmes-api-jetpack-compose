package com.marcelo.souza.api.filmes.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.marcelo.souza.api.filmes.R
import com.marcelo.souza.api.filmes.domain.model.DetailsMovieViewData
import com.marcelo.souza.api.filmes.presentation.components.TopAppBarComponent
import com.marcelo.souza.api.filmes.presentation.theme.ApiMoviesTheme
import com.marcelo.souza.api.filmes.presentation.theme.LocalDimens
import com.marcelo.souza.api.filmes.presentation.utils.Constants.MOVIE_CARD_IMAGE_ASPECT_RATIO

@Composable
fun MovieDetailsScreen(
    movie: DetailsMovieViewData,
    onBackClick: () -> Unit = {}
) {
    val dimen = LocalDimens.current
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                add(OkHttpNetworkFetcherFactory())
            }
            .crossfade(true)
            .build()
    }

    val imageRequest = remember(movie.imageUrl) {
        ImageRequest.Builder(context)
            .data(movie.imageUrl)
            .crossfade(true)
            .build()
    }

    Scaffold(
        topBar = {
            TopAppBarComponent(
                title = movie.name,
                showBackButton = true,
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(
                    bottomStart = dimen.size16,
                    bottomEnd = dimen.size16
                ),
                tonalElevation = 2.dp
            ) {
                AsyncImage(
                    model = imageRequest,
                    contentDescription = movie.name,
                    modifier = Modifier
                        .aspectRatio(MOVIE_CARD_IMAGE_ASPECT_RATIO)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                    imageLoader = imageLoader,
                    placeholder = null,
                    error = null
                )
            }

            Spacer(modifier = Modifier.height(dimen.size16))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimen.size16)
            ) {
                Text(
                    text = movie.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .testTag("movieTitle")
                        .padding(bottom = dimen.size8)
                )

                Text(
                    text = movie.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = dimen.size16)
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = MaterialTheme.typography.titleMedium.toSpanStyle().copy(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            append(stringResource(R.string.title_movies_details_cast))
                            append(" ")
                        }
                        withStyle(
                            style = MaterialTheme.typography.bodyLarge.toSpanStyle()
                                .copy(color = MaterialTheme.colorScheme.onSurface)
                        ) {
                            append(movie.cast)
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(dimen.size24))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailsScreenPreviewDark() {
    ApiMoviesTheme(darkTheme = true) {
        MovieDetailsScreen(
            movie = DetailsMovieViewData(
                id = 1,
                name = "House of the Dragon",
                imageUrl = "urlImagem",
                description = "A luta pelo Trono de Ferro começa muito antes de Game of Thrones. " +
                        "Fogo, sangue e destino se entrelaçam.",
                cast = "Emma D'Arcy, Matt Smith"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailsScreenPreviewLight() {
    ApiMoviesTheme(darkTheme = false) {
        MovieDetailsScreen(
            movie = DetailsMovieViewData(
                id = 1,
                name = "House of the Dragon",
                imageUrl = "urlImagem",
                description = "A luta pelo Trono de Ferro começa muito antes de Game of Thrones. " +
                        "Fogo, sangue e destino se entrelaçam.",
                cast = "Emma D'Arcy, Matt Smith"
            )
        )
    }
}