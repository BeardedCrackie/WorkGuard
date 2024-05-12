
package sk.potociarm.workguard.ui.events

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import sk.potociarm.workguard.R
import sk.potociarm.workguard.WorkGuardTopAppBar
import sk.potociarm.workguard.data.workevent.WorkEvent
import sk.potociarm.workguard.ui.AppViewModelProvider
import sk.potociarm.workguard.ui.events.component.WorkEventCard
import sk.potociarm.workguard.ui.navigation.NavDestination
import sk.potociarm.workguard.ui.theme.WorkGuardTheme

object WorkEventListDestination : NavDestination {
    override val route = "event_list"
    override val titleRes = R.string.app_name
}

/**
 * Entry route for WorkTagList screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkEventListScreen(
    navigateToWorkEventEntry: () -> Unit,
    navigateToWorkEventUpdate: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkEventListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val workEventMap = viewModel.eventMap.collectAsState() //todo load state
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            WorkGuardTopAppBar(
                title = stringResource(WorkEventListDestination.titleRes),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToWorkEventEntry,
                shape = MaterialTheme.shapes.medium,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.work_tag_list)
                )
            }
        },
    ) { innerPadding->
        WorkEventListBody(
            onItemClick = navigateToWorkEventUpdate,
            workEventsMap = workEventMap.value,
            contentPadding = innerPadding
        )
    }
}

@Composable
private fun WorkEventListBody(
    workEventsMap: Map<String, List<WorkEvent>>,
    onItemClick: (Int) -> Unit,
    contentPadding: PaddingValues = PaddingValues(all = dimensionResource(id = R.dimen.padding_small)),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (workEventsMap.isEmpty()) {
            Text(
                text = stringResource(R.string.no_tag_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            for(eventList in workEventsMap) {
                WorkEventDateList(
                    date = eventList.key,
                    eventsInDate = eventList.value,
                    onItemClick = { onItemClick(it.id) },
                    contentPadding = contentPadding
                )
            }
        }
    }
}

@Composable
private fun WorkEventDateList(
    date: String,
    eventsInDate: List<WorkEvent>,
    onItemClick: (WorkEvent) -> Unit,
    contentPadding: PaddingValues,
    expanded: Boolean = false
) {

    var isExpanded by remember { mutableStateOf(expanded) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { isExpanded = !isExpanded })
            .padding(contentPadding)
    ) {
        // Show the date header
        WorkEventDateHeader(date = date)

        // Show the work events if the Column is expanded
        if (isExpanded) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(eventsInDate) { workEvent ->
                    WorkEventCard(
                        event = workEvent.toWorkEventState(),
                        eventTag = null, //todo null tag
                        modifier = Modifier
                            .padding(contentPadding)
                            .clickable { onItemClick(workEvent) }
                    )
                }
            }
        }
    }
}

@Composable
fun WorkEventDateHeader(date: String) {
    Text(
        text = date,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Preview(showBackground = true)
@Composable
fun WorkEventBodyPreview() {
    WorkGuardTheme {
        WorkEventListBody(
            workEventsMap = sampleEventMap(),
            onItemClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WorkEventEmptyListPreview() {
    WorkGuardTheme {
        WorkEventListBody(
            workEventsMap = sampleEmptyEventMap(),
            onItemClick = {}
        )
    }
}

@Preview
@Composable
fun WorkEventDateListPreview() {
    WorkGuardTheme {
        WorkEventDateList(
            date = "01.01.2024",
            eventsInDate = sampleEventList(),
            expanded = true,
            onItemClick = {},
            contentPadding = PaddingValues(all = dimensionResource(id = R.dimen.padding_small))
        )
    }
}

