package sk.potociarm.workguard.ui.events.component

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
import com.example.compose.WorkGuardTheme
import sk.potociarm.workguard.data.worktag.WorkTag
import sk.potociarm.workguard.ui.events.WorkEventState
import sk.potociarm.workguard.ui.events.sampleEventWithTag
import sk.potociarm.workguard.ui.events.sampleEventWithoutTag
import sk.potociarm.workguard.ui.tags.sampleTagWithoutParent


@Composable
fun WorkEventCard(
    event: WorkEventState,
    eventTag: WorkTag?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.inversePrimary,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row(
            modifier = modifier
        ) {
            Column {
                Text(
                    text = event.name,
                    style = MaterialTheme.typography.titleLarge
                )
                if (eventTag != null) {
                    Text(
                        text = eventTag.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = event.getRunTime().toString(),
                    style = MaterialTheme.typography.titleLarge
                )
                /*
                Text(
                    text = "${event.computeEarn()} $RATE_SYMBOL",
                    style = MaterialTheme.typography.headlineSmall
                )
                 */

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkEventCardPreview() {
    WorkGuardTheme {
        WorkEventCard(
            event = sampleEventWithTag(),
            eventTag = sampleTagWithoutParent(),
        )
    }
}


@Preview(showBackground = true)
@Composable
fun WorkEventCardWithoutTagPreview() {
    WorkGuardTheme {
        WorkEventCard(
            event = sampleEventWithoutTag(),
            eventTag = null,
        )
    }
}
