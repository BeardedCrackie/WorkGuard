
package sk.potociarm.workguard.ui.events

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.compose.WorkGuardTheme
import kotlinx.datetime.LocalDate
import sk.potociarm.workguard.R
import sk.potociarm.workguard.RATE_SYMBOL
import sk.potociarm.workguard.WorkGuardTopAppBar
import sk.potociarm.workguard.data.workevent.WorkEvent
import sk.potociarm.workguard.ui.AppViewModelProvider
import sk.potociarm.workguard.ui.events.component.WorkEventCard
import sk.potociarm.workguard.ui.navigation.NavDestination

object WorkEventListDestination : NavDestination {
    override val route = "event_list"
    override val titleRes = R.string.work_events_screen_title
}

/**
 * Entry route for WorkTagList screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkEventListScreen(
    navigateToWorkEventEntry: () -> Unit,
    navigateToWorkEventUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkEventListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    openNavigation: () -> Unit
) {
    val workEventMap = viewModel.eventMap.collectAsState() //todo load state
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            WorkGuardTopAppBar(
                title = stringResource(WorkEventListDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                openNavigation = openNavigation
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
    workEventsMap: Map<LocalDate, List<WorkEvent>>,
    onItemClick: (Int) -> Unit,
    contentPadding: PaddingValues = PaddingValues(all = dimensionResource(id = R.dimen.padding_small)),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (workEventsMap.isEmpty()) {
            Text(
                text = stringResource(R.string.no_event_description),
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
    date: LocalDate,
    eventsInDate: List<WorkEvent>,
    onItemClick: (WorkEvent) -> Unit,
    contentPadding: PaddingValues,
    expanded: Boolean = false
) {

    var isExpanded by remember { mutableStateOf(expanded) }

    Card(
        modifier = Modifier
            .padding(all = dimensionResource(R.dimen.padding_small)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { isExpanded = !isExpanded })
                .padding(contentPadding),
        ) {
            // Show the date header
            WorkEventDateHeader(
                modifier = Modifier.padding(all = dimensionResource(id = R.dimen.padding_extra_small)),
                date = date,
                dateWorkDuration = printRunningTime(eventsInDate),
                dateWorkEarn = printDateEarn(eventsInDate)
            )

            // Show the work events if the Column is expanded
            if (isExpanded) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.inversePrimary,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ))
                    {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            items(eventsInDate) { workEvent ->
                                WorkEventCard(
                                    event = workEvent.toWorkEventState(),
                                    eventTag = null, //todo null tag
                                    modifier = Modifier
                                        .padding(all = dimensionResource(id = R.dimen.padding_extra_small))
                                        .clickable { onItemClick(workEvent) }
                                )
                            }
                        }
                    }
            }
        }
    }
}

fun printDateEarn(eventsInDate: List<WorkEvent>): String {
    var totalEarn = 0.0
    for (event in eventsInDate) {
        totalEarn += event.toWorkEventState().computeEarn()
    }
    return "$totalEarn $RATE_SYMBOL"
}

fun printRunningTime(eventsInDate: List<WorkEvent>): String {
    var totalRunTime = 0
    for (event in eventsInDate) {
        totalRunTime += event.toWorkEventState().getRunTime().toSecondOfDay()
    }
    val hours = totalRunTime / 3600
    totalRunTime %= 3600
    val minutes = totalRunTime / 60
    totalRunTime %= 60
    return "$hours:$minutes"
}

@Composable
fun WorkEventDateHeader(
    modifier: Modifier = Modifier,
    date: LocalDate,
    dateWorkDuration: String,
    dateWorkEarn: String
) {
    Row(
        modifier = modifier,
    )
    {
        Column()
        {
            Text(
                text = date.toString(),
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = dateWorkDuration,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = dateWorkEarn,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
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
            date = LocalDate(year = 2024, dayOfMonth = 1, monthNumber = 1),
            eventsInDate = sampleEventList(),
            expanded = true,
            onItemClick = {},
            contentPadding = PaddingValues(all = dimensionResource(id = R.dimen.padding_small))
        )
    }
}

