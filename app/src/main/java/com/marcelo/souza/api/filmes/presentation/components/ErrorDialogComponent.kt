package com.marcelo.souza.api.filmes.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.marcelo.souza.api.filmes.R
import com.marcelo.souza.api.filmes.presentation.theme.ApiMoviesTheme
import com.marcelo.souza.api.filmes.presentation.theme.White
import com.patrik.fancycomposedialogs.dialogs.ErrorFancyDialog
import com.patrik.fancycomposedialogs.enums.DialogActionType
import com.patrik.fancycomposedialogs.enums.DialogStyle
import com.patrik.fancycomposedialogs.properties.DialogButtonProperties

@Composable
fun ErrorDialog(
    title: String,
    message: String,
    isCancelable: Boolean,
    isErrorServer: Boolean,
    dialogButtonProperties: DialogButtonProperties,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit
) {
    val dialogType = if (isErrorServer) {
        DialogActionType.INFORMATIVE
    } else {
        DialogActionType.ACTIONABLE
    }
    ErrorFancyDialog(
        title = title,
        showTitle = true,
        showMessage = true,
        message = message,
        isCancelable = isCancelable,
        dialogActionType = dialogType,
        dialogProperties = dialogButtonProperties,
        dialogStyle = DialogStyle.UPPER_CUTTING,
        positiveButtonClick = {
            onConfirmClick.invoke()
        },
        neutralButtonClick = {
            onDismissClick.invoke()
        },
        negativeButtonClick = {
            onDismissClick.invoke()
        },
        dismissTouchOutside = onDismissClick
    )
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
internal fun PreviewErrorDialog() {
    ApiMoviesTheme {
        ErrorDialog(
            title = stringResource(R.string.title_show_movies_error_dialog),
            message = stringResource(id = R.string.message_show_movies_error_dialog),
            isCancelable = true,
            isErrorServer = false,
            dialogButtonProperties = DialogButtonProperties(
                neutralButtonText = R.string.txt_btn_neutral_error_server_dialog,
                positiveButtonText = R.string.txt_btn_positive_try_again_error_dialog,
                negativeButtonText = R.string.txt_btn_negative_cancel_dialog,
                buttonColor = MaterialTheme.colorScheme.primary,
                buttonTextColor = White
            ),
            onConfirmClick = {},
            onDismissClick = {}
        )
    }
}