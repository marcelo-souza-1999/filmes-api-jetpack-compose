package com.marcelo.souza.api.filmes.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.marcelo.souza.api.filmes.R
import com.marcelo.souza.api.filmes.presentation.theme.ApiMoviesTheme
import com.marcelo.souza.api.filmes.presentation.utils.Constants.MOVIE_CARD_ASPECT_RATIO

@Composable
fun MovieCategoriesSectionComponent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(
            count = 4,
            key = { categoriesIndex -> "category_$categoriesIndex" }
        ) { categoriesIndex ->
            Column(
                modifier = Modifier
                    .padding(bottom = dimensionResource(R.dimen.size_16))
                    .animateItem()
            ) {
                Text(
                    text = "Ação",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .testTag("titleCategory")
                        .padding(
                            start = dimensionResource(R.dimen.size_8),
                            bottom = dimensionResource(R.dimen.size_8)
                        )
                )

                LazyRow(
                    contentPadding = PaddingValues(
                        horizontal = dimensionResource(R.dimen.size_8)
                    )
                ) {
                    items(
                        items = List(6) { it },
                        key = { movieIndex -> "category_${categoriesIndex}_movie_$movieIndex" }
                    ) { movieIndex ->
                        MovieCard(
                            modifier = Modifier
                                .padding(end = dimensionResource(R.dimen.size_8))
                                .width(dimensionResource(R.dimen.size_120))
                                .aspectRatio(MOVIE_CARD_ASPECT_RATIO),
                            posterRes = R.drawable.preview,
                            posterDescription = "Cartaz do filme $movieIndex"
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
    posterRes: Int,
    posterDescription: String
) {
    val placeholderPainter = painterResource(id = R.drawable.loading)
    val errorPainter = painterResource(id = android.R.drawable.ic_menu_report_image)

    Card(
        modifier = modifier.testTag("cardMovie"),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.size_4))
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = posterRes,
                contentDescription = posterDescription,
                placeholder = placeholderPainter,
                error = errorPainter,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = posterDescription }
            )
        }
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
        MovieCategoriesSectionComponent(
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.size_8))
                .padding(top = dimensionResource(R.dimen.size_8)),
            contentPadding = PaddingValues(8.dp)
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
        MovieCategoriesSectionComponent(
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.size_8))
                .padding(top = dimensionResource(R.dimen.size_8)),
            contentPadding = PaddingValues(8.dp)
        )
    }
}