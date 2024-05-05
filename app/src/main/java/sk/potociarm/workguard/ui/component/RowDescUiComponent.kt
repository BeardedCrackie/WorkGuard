package sk.potociarm.workguard.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight

@Composable
fun RowDescUiComponent(
    @StringRes labelResID: Int, textValue: String?, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Text(
            stringResource(labelResID),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.weight(1f))
        if (textValue != null) {
            Text(
                text = textValue,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}