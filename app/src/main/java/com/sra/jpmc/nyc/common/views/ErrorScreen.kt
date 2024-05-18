package com.sra.jpmc.nyc.common.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sra.jpmc.nyc.R
import com.sra.jpmc.nyc.ui.theme.Typography

/**
 * Displays an error screen with a centered error message text and an icon.
 * @param errorMessage the error message to display in the center of the screen.
 */
@Composable
fun ErrorScreen(
    errorMessage: String = stringResource(id = R.string.genreic_error), onRetryClick: (() -> Unit)
) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_error),
                contentDescription = stringResource(R.string.accesibility_error_icon),
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = errorMessage, style = Typography.subtitle1, textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(stringResource(R.string.retry)) {
                onRetryClick.invoke()
            }
        }
    }
}
