package com.marcelo.souza.api.filmes.presentation.components

import android.content.res.Configuration
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.marcelo.souza.api.filmes.presentation.theme.ApiMoviesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComponent(
    title: String,
    showBackButton: Boolean,
    onBackClick: (() -> Unit)? = null
) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    TopAppBar(
        modifier = Modifier
            .testTag("topAppBarComponent"),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface
        ),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(
                    onClick = {
                        onBackClick?.invoke() ?: backDispatcher?.onBackPressed()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    )
}

@Preview(
    showBackground = true,
    name = "Light TopBar"
)
@Composable
fun TopAppBarPreviewLight() {
    ApiMoviesTheme {
        TopAppBarComponent(title = "Filmes", showBackButton = false)
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark TopBar"
)
@Composable
fun TopAppBarPreviewDark() {
    ApiMoviesTheme(darkTheme = true) {
        TopAppBarComponent(title = "Filmes", showBackButton = true)
    }
}