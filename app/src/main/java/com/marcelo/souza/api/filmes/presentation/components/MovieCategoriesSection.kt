package com.marcelo.souza.api.filmes.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.marcelo.souza.api.filmes.domain.model.CategoryViewData
import com.marcelo.souza.api.filmes.domain.model.DetailsMovieViewData
import com.marcelo.souza.api.filmes.presentation.theme.ApiMoviesTheme
import com.marcelo.souza.api.filmes.presentation.theme.LocalDimens
import com.marcelo.souza.api.filmes.presentation.utils.Constants.MOVIE_CARD_ASPECT_RATIO

@Composable
fun MovieCategoriesSectionComponent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    categories: List<CategoryViewData>,
    onMovieClick: (DetailsMovieViewData) -> Unit
) {
    val dimen = LocalDimens.current

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(
            items = categories,
            key = { category -> "category_${category.id}" }
        ) { category ->
            Column(
                modifier = Modifier
                    .padding(bottom = dimen.size16)
                    .animateItem()
            ) {
                Text(
                    text = category.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .testTag("titleCategory")
                        .padding(
                            start = dimen.size8,
                            bottom = dimen.size8
                        )
                )

                LazyRow(
                    contentPadding = PaddingValues(
                        horizontal = dimen.size8
                    )
                ) {
                    items(
                        items = category.cover,
                        key = { movie -> "category_${category.id}_movie_${movie.id}" }
                    ) { movie ->
                        MovieCard(
                            modifier = Modifier
                                .padding(end = dimen.size8)
                                .width(dimen.size120),
                            posterUrl = movie.imageUrl,
                            onClick = { onMovieClick(movie) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    posterUrl: String,
    onClick: () -> Unit = {}
) {
    val dimen = LocalDimens.current
    val context = LocalContext.current

    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                add(OkHttpNetworkFetcherFactory())
            }
            .crossfade(true)
            .build()
    }

    Card(
        modifier = modifier
            .testTag("cardMovie")
            .aspectRatio(MOVIE_CARD_ASPECT_RATIO)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = dimen.size4)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(posterUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            error = painterResource(android.R.drawable.ic_menu_report_image),
            imageLoader = imageLoader,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(
    showBackground = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark CategoriesMoviesList"
)
@Composable
fun CategoriesMoviesListPreviewDark() {
    ApiMoviesTheme(darkTheme = true) {
        val dimen = LocalDimens.current
        MovieCategoriesSectionComponent(
            modifier = Modifier
                .padding(horizontal = dimen.size8)
                .padding(top = dimen.size8),
            contentPadding = PaddingValues(8.dp),
            categories = mockCategoryData,
            onMovieClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "Light CategoriesMoviesList"
)
@Composable
fun CategoriesMoviesListPreviewLight() {
    ApiMoviesTheme {
        val dimen = LocalDimens.current
        MovieCategoriesSectionComponent(
            modifier = Modifier
                .padding(horizontal = dimen.size8)
                .padding(top = dimen.size8),
            contentPadding = PaddingValues(8.dp),
            categories = mockCategoryData,
            onMovieClick = {}
        )
    }
}

private val mockCategoryData = listOf(
    CategoryViewData(
        id = 1,
        title = "Filmes de ação",
        cover = listOf(
            DetailsMovieViewData(
                id = 7,
                name = "Hanna",
                imageUrl = "https://stackmobile.com.br/wp-content/uploads/2022/10/filme.jpg",
                description = "Descrição de Ação 1",
                cast = "Elenco de Ação 1"
            ),
            DetailsMovieViewData(
                id = 8,
                name = "Projeto Gemini",
                imageUrl = "https://stackmobile.com.br/wp-content/uploads/2022/10/filme2.jpg",
                description = "Descrição de Ação 2",
                cast = "Elenco de Ação 2"
            ),
            DetailsMovieViewData(
                id = 9,
                name = "Rambo",
                imageUrl = "https://stackmobile.com.br/wp-content/uploads/2022/10/filme3.jpg",
                description = "Descrição de Ação 3",
                cast = "Elenco de Ação 3"
            ),
            DetailsMovieViewData(
                id = 10,
                name = "J. Wick",
                imageUrl = "https://stackmobile.com.br/wp-content/uploads/2022/10/filme4.jpg",
                description = "Descrição de Ação 4",
                cast = "Elenco de Ação 4"
            ),
        )
    ),
    CategoryViewData(
        id = 2,
        title = "Aventura",
        cover = listOf(
            DetailsMovieViewData(
                id = 11,
                name = "Mumia",
                imageUrl = "https://stackmobile.com.br/wp-content/uploads/2022/10/filme5.jpg",
                description = "Descrição de Aventura 1",
                cast = "Elenco de Aventura 1"
            ),
            DetailsMovieViewData(
                id = 12,
                name = "Sete Anos no Tibet",
                imageUrl = "https://stackmobile.com.br/wp-content/uploads/2022/10/filme6.jpg",
                description = "Descrição de Aventura 2",
                cast = "Elenco de Aventura 2"
            ),
            DetailsMovieViewData(
                id = 13,
                name = "Mercenários",
                imageUrl = "https://stackmobile.com.br/wp-content/uploads/2022/10/filme7.jpg",
                description = "Descrição de Aventura 3",
                cast = "Elenco de Aventura 3"
            ),
            DetailsMovieViewData(
                id = 14,
                name = "Indiana",
                imageUrl = "https://stackmobile.com.br/wp-content/uploads/2022/10/filme.jpg",
                description = "Descrição de Aventura 4",
                cast = "Elenco de Aventura 4"
            ),
        )
    )
)