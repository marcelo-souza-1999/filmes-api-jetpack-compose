package com.marcelo.souza.api.filmes.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marcelo.souza.api.filmes.presentation.theme.ApiMoviesTheme
import com.marcelo.souza.api.filmes.presentation.theme.LocalDimens
import com.marcelo.souza.api.filmes.presentation.utils.Constants.MOVIE_CARD_ASPECT_RATIO
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun MovieCategoriesSectionShimmer(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp)
) {
    val dimen = LocalDimens.current
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Window)

    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(count = 6) { _ ->
            Column {
                Box(
                    modifier = Modifier
                        .width(dimen.size120)
                        .height(dimen.size24)
                        .shimmer(shimmerInstance)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )

                Spacer(modifier = Modifier.height(dimen.size8))

                LazyRow {
                    items(count = 6) { _ ->
                        Box(
                            modifier = Modifier
                                .width(dimen.size120)
                                .aspectRatio(MOVIE_CARD_ASPECT_RATIO)
                                .shimmer(shimmerInstance)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .height(dimen.size300)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(dimen.size16))
            }
        }
    }
}

@Preview(
    name = "Shimmer - Dark",
    showBackground = false,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun MovieCategoriesSectionShimmerPreviewDark() {
    ApiMoviesTheme(darkTheme = true) {
        MovieCategoriesSectionShimmer(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )
    }
}

@Preview(name = "Shimmer - Light", showBackground = true)
@Composable
fun MovieCategoriesSectionShimmerPreviewLight() {
    ApiMoviesTheme(darkTheme = false) {
        MovieCategoriesSectionShimmer(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )
    }
}