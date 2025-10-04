package com.marcelo.souza.api.filmes.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.marcelo.souza.api.filmes.R
import com.marcelo.souza.api.filmes.presentation.components.TopAppBarComponent
import com.marcelo.souza.api.filmes.presentation.theme.ApiMoviesTheme

@Composable
fun MovieDetailsScreen(
    title: String,
    description: String,
    cast: String,
    imageUrlOrRes: Any? = null,
    onBackClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val windowInfo = LocalWindowInfo.current
    val screenWidth = windowInfo.containerSize.width
    val responsiveHeight = (screenWidth * 0.45f).dp

    Scaffold(
        topBar = {
            TopAppBarComponent(
                title = stringResource(R.string.title_movies_details_top_app_bar),
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
            MovieCover(
                image = imageUrlOrRes,
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(responsiveHeight)
                    .padding(bottom = dimensionResource(R.dimen.size_16))
            )

            DetailsContent(
                titleText = title,
                descriptionText = description,
                castText = cast,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.size_16))
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.size_24)))
        }
    }
}

@Composable
private fun MovieCover(
    image: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(
            bottomStart = dimensionResource(R.dimen.size_16),
            bottomEnd = dimensionResource(R.dimen.size_16)
        ),
        tonalElevation = 2.dp
    ) {
        MovieImage(
            image = image,
            contentDescription = contentDescription
        )
    }
}

@Composable
private fun MovieImage(image: Any?, contentDescription: String?) {
    val context = LocalContext.current
    val placeholder = painterResource(id = R.drawable.loading)
    val error = painterResource(id = android.R.drawable.ic_menu_report_image)

    val request = remember(image) {
        ImageRequest.Builder(context)
            .data(image ?: R.drawable.preview)
            .crossfade(true)
            .crossfade(300)
            .build()
    }

    AsyncImage(
        model = request,
        contentDescription = contentDescription,
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.Crop,
        placeholder = placeholder,
        error = error
    )
}

@Composable
private fun DetailsContent(
    titleText: String,
    descriptionText: String,
    castText: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = titleText,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.size_8))
        )

        Text(
            text = descriptionText,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.size_16))
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
                    append(castText)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailsScreenPreviewDark() {
    ApiMoviesTheme(darkTheme = true) {
        MovieDetailsScreen(
            title = "House of the Dragon",
            description = "A luta pelo Trono de Ferro começa muito antes de Game of Thrones. Fogo, sangue e destino se entrelaçam.",
            cast = "Emma D'Arcy, Matt Smith",
            imageUrlOrRes = R.drawable.preview
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailsScreenPreviewLight() {
    ApiMoviesTheme(darkTheme = false) {
        MovieDetailsScreen(
            title = "House of the Dragon",
            description = "A luta pelo Trono de Ferro começa muito antes de Game of Thrones. Fogo, sangue e destino se entrelaçam.",
            cast = "Emma D'Arcy, Matt Smith",
            imageUrlOrRes = R.drawable.preview
        )
    }
}