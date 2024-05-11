package sk.potociarm.workguard.ui.events.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.potociarm.workguard.R
import sk.potociarm.workguard.ui.component.RowDescUiComponent
import sk.potociarm.workguard.ui.events.WorkEventState
import sk.potociarm.workguard.ui.events.sampleEventWithTag
import sk.potociarm.workguard.ui.events.sampleRunningEventWithTag
import sk.potociarm.workguard.ui.tags.WorkTagState
import sk.potociarm.workguard.ui.tags.component.WorkTagCard
import sk.potociarm.workguard.ui.tags.sampleTagUiWithParent
import sk.potociarm.workguard.ui.theme.WorkGuardTheme

@Composable
fun WorkEventCard(
    modifier: Modifier = Modifier,
    event: WorkEventState,
    eventTag: WorkTagState?,
    navigateToWorkTag: (Int) -> Unit,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row(
            modifier = modifier
        ) {
            Column {
                RowDescUiComponent(
                    labelResID = R.string.work_event_name,
                    textValue = event.name
                )
                RowDescUiComponent(
                    labelResID = R.string.work_event_start_time,
                    textValue = event.getStartTime()
                )
                RowDescUiComponent(
                    labelResID = R.string.work_event_start_date,
                    textValue = event.getStartDate()
                )
                RowDescUiComponent(
                    labelResID = R.string.work_event_end_time,
                    textValue = event.getEndTime()
                )
                RowDescUiComponent(
                    labelResID = R.string.work_event_end_date,
                    textValue = event.getEndDate()
                )
                RowDescUiComponent(
                    labelResID = R.string.work_event_run_time,
                    textValue = event.getRunTime()
                )
                RowDescUiComponent(
                    labelResID = R.string.work_event_description,
                    textValue = event.description
                )
                RowDescUiComponent(
                    labelResID = R.string.work_event_description,
                    textValue = event.description
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant,
                    thickness = 1.dp
                )
                if (event.tagId != null) {
                    WorkTagCard(
                        tag = eventTag!!, //if parentId is null, then parent does not exists
                        parentTag = null,
                        modifier = Modifier.clickable {
                            navigateToWorkTag(eventTag.id)
                        }
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.no_tag_parent),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkEventCardPreview() {
    WorkGuardTheme {
        WorkEventCard(
            modifier = Modifier,
            event = sampleEventWithTag(),
            eventTag = sampleTagUiWithParent(),
            navigateToWorkTag = {}
        )
    }
}


@Preview(showBackground = true)
@Composable
fun WorkEventCardRunningPreview() {
    WorkGuardTheme {
        WorkEventCard(
            modifier = Modifier,
            event = sampleRunningEventWithTag(),
            eventTag = sampleTagUiWithParent(),
            navigateToWorkTag = {}
        )
    }
}

