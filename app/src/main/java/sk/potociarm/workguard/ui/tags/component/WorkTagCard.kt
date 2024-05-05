package sk.potociarm.workguard.ui.tags.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import sk.potociarm.workguard.R
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
        modifier = modifier.padding(all = dimensionResource(id = R.dimen.padding_small)),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(
                id = R.dimen.elevation
            )
        ),
        /*
        border = BorderStroke(
            dimensionResource(
                id = R.dimen.borderSize
            ),
            color = MaterialTheme.colorScheme.outlineVariant,
        ),
        */
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row (
            modifier = modifier
                .padding(all = dimensionResource(id = R.dimen.padding_small)
                )
        ) {
            Column(modifier = modifier) {
                Text(
                    text = tag.name,
                    style = MaterialTheme.typography.headlineMedium
                )
                if (parentTag != null) {
                    Text(
                        text = parentTag.name,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
            Spacer(modifier = modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = tag.price.toString(),
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = " €/h", //todo currency
                    style = MaterialTheme.typography.headlineSmall
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
