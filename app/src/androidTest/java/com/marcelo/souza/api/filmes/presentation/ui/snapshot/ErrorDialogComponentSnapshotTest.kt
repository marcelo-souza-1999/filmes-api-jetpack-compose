package com.marcelo.souza.api.filmes.presentation.ui.snapshot

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import com.karumi.shot.ScreenshotTest
import com.marcelo.souza.api.filmes.R
import com.marcelo.souza.api.filmes.presentation.components.ErrorDialog
import com.marcelo.souza.api.filmes.presentation.theme.ApiMoviesTheme
import com.marcelo.souza.api.filmes.presentation.theme.White
import com.patrik.fancycomposedialogs.properties.DialogButtonProperties
import org.junit.Rule
import org.junit.Test

class ErrorDialogComponentSnapshotTest : ScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun getMockedStrings(): Pair<String, String> {
        val title = "Erro de Conexão"
        val message = "Não foi possível carregar os dados. Verifique sua conexão e tente novamente."
        return Pair(title, message)
    }

    @Composable
    private fun getButtonProperties(): DialogButtonProperties {
        return DialogButtonProperties(
            neutralButtonText = R.string.txt_btn_neutral_error_server_dialog,
            positiveButtonText = R.string.txt_btn_positive_try_again_error_dialog,
            negativeButtonText = R.string.txt_btn_negative_cancel_dialog,
            buttonColor = MaterialTheme.colorScheme.primary, // OK
            buttonTextColor = White
        )
    }

    @Test
    fun errorDialog_actionable_light_snapshot() {
        val (title, message) = getMockedStrings()
        composeTestRule.setContent {
            ApiMoviesTheme(darkTheme = false) {
                val buttonProps = getButtonProperties()

                ErrorDialog(
                    title = title,
                    message = message,
                    isCancelable = true,
                    isErrorServer = false,
                    dialogButtonProperties = buttonProps,
                    onConfirmClick = {},
                    onDismissClick = {}
                )
            }
        }

        compareScreenshot(
            node = composeTestRule.onNode(hasText(title)),
            name = "error_dialog_actionable_light"
        )
    }

    @Test
    fun errorDialog_informative_light_snapshot() {
        val (title, message) = getMockedStrings()
        composeTestRule.setContent {
            ApiMoviesTheme(darkTheme = false) {
                val buttonProps = getButtonProperties()

                ErrorDialog(
                    title = title,
                    message = message,
                    isCancelable = true,
                    isErrorServer = true,
                    dialogButtonProperties = buttonProps,
                    onConfirmClick = {},
                    onDismissClick = {}
                )
            }
        }

        compareScreenshot(
            node = composeTestRule.onNode(hasText(title)),
            name = "error_dialog_informative_light"
        )
    }

    @Test
    fun errorDialog_actionable_dark_snapshot() {
        val (title, message) = getMockedStrings()
        composeTestRule.setContent {
            ApiMoviesTheme(darkTheme = true) {
                val buttonProps = getButtonProperties()

                ErrorDialog(
                    title = title,
                    message = message,
                    isCancelable = true,
                    isErrorServer = false,
                    dialogButtonProperties = buttonProps,
                    onConfirmClick = {},
                    onDismissClick = {}
                )
            }
        }

        compareScreenshot(
            node = composeTestRule.onNode(hasText(title)),
            name = "error_dialog_actionable_dark"
        )
    }
}