package sk.potociarm.workguard.ui.events

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.WorkGuardTheme
import sk.potociarm.workguard.R
import sk.potociarm.workguard.WorkGuardTopAppBar
import sk.potociarm.workguard.ui.AppViewModelProvider
import sk.potociarm.workguard.ui.component.RowDescUiComponent
import sk.potociarm.workguard.ui.navigation.NavDestination
import sk.potociarm.workguard.ui.tags.WorkTagDetailsDestination
import sk.potociarm.workguard.ui.tags.WorkTagState
import sk.potociarm.workguard.ui.tags.component.WorkTagCard
import sk.potociarm.workguard.ui.tags.sampleTagUiWithParent


object WorkEventDetailsDestination : NavDestination {
    override val route = "work_event_details"
    override val titleRes = R.string.workevent_detail_title
    const val ID_ARG = "id"
    val routeWithArgs = "$route/{$ID_ARG}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkEventDetailsScreen(
    navigateToEditWorkEvent: (Int) -> Unit,
    navigateToWorkTag: (Int) -> Unit,
    navigateBack: () -> Unit,
    viewModel: WorkEventDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val eventState = viewModel.eventUiState.collectAsState()
    val eventTagState = viewModel.tagUiState?.collectAsState()
    Scaffold(
        topBar = {
            WorkGuardTopAppBar(
                title = stringResource(WorkTagDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditWorkEvent(eventState.value.id) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateEndPadding(LocalLayoutDirection.current)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.work_tag_edit),
                )
            }
        },
    ) { innerPadding ->
        WorkEventDetailsBody(
            event = eventState.value,
            eventTag = eventTagState?.value,
            navigateToWorkTag = navigateToWorkTag,
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState()
        ))
    }
}

@Composable
private fun WorkEventDetailsBody(
    event: WorkEventState,
    eventTag: WorkTagState?,
    navigateToWorkTag: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        WorkEventDetailCard(
            modifier = Modifier,
            event = event,
            eventTag = eventTag,
            navigateToWorkTag = navigateToWorkTag
        )
    }
}

@Composable
fun WorkEventDetailCard(
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
                    labelResID = R.string.work_event_start_date,
                    textValue = event.date.toString()
                )
                RowDescUiComponent(
                    labelResID = R.string.work_event_start_time,
                    textValue = event.startTime.toString()
                )
                RowDescUiComponent(
                    labelResID = R.string.work_event_end_time,
                    textValue = event.endTime.toString()
                )
                RowDescUiComponent(
                    labelResID = R.string.work_event_run_time,
                    textValue = event.getRunTime().toString()
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
        WorkEventDetailCard(
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
        WorkEventDetailCard(
            modifier = Modifier,
            event = sampleRunningEventWithTag(),
            eventTag = sampleTagUiWithParent(),
            navigateToWorkTag = {}
        )
    }
}
