package sk.potociarm.workguard.ui.tags.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import sk.potociarm.workguard.HOUR_RATE_SYMBOL
import sk.potociarm.workguard.data.worktag.WorkTag
import sk.potociarm.workguard.ui.tags.WorkTagUi
import sk.potociarm.workguard.ui.tags.sampleTagUiWithParent
import sk.potociarm.workguard.ui.tags.sampleTagWithoutParent
import sk.potociarm.workguard.ui.theme.WorkGuardTheme

@Composable
fun WorkTagCard(
    tag: WorkTagUi,
    parentTag: WorkTag?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        /* //todo elevation/border
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(
                id = R.dimen.elevation
            )
        ),
         */
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row(
            modifier = modifier
        ) {
            Column {
                Text(
                    text = tag.name,
                    style = MaterialTheme.typography.headlineMedium
                )
                if (parentTag != null) {
                    Text(
                        text = "parent: ${parentTag.name}",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${tag.price} $HOUR_RATE_SYMBOL",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkTagCardPreview() {
    WorkGuardTheme {
        WorkTagCard(
            tag = sampleTagUiWithParent(),
            parentTag = sampleTagWithoutParent(),
        )
    }
}
