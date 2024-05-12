
package sk.potociarm.workguard.ui.events

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.WorkGuardTheme
import kotlinx.coroutines.launch
import sk.potociarm.workguard.R
import sk.potociarm.workguard.WorkGuardTopAppBar
import sk.potociarm.workguard.ui.AppViewModelProvider
import sk.potociarm.workguard.ui.events.component.WorkEventFormCard
import sk.potociarm.workguard.ui.navigation.NavDestination

object WorkEventEntryDestination : NavDestination {
    override val route = "workTag_entry"
    override val titleRes = R.string.workTag_entry_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkEventEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: WorkEventEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val tagListState by viewModel.otherTagsUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            WorkGuardTopAppBar(
                title = stringResource(WorkEventEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp,
            )
        }
    ) { innerPadding ->
        WorkEventFormCard(
            initialWorkEvent = viewModel.eventState,
            onButtonClick = {
                coroutineScope.launch {
                    viewModel.saveWorkEvent()
                    navigateBack()
                }
            },
            onEventStateChange = viewModel::updateUiState,
            contentPadding = innerPadding
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WorkEntryScreenPreview() {
    WorkGuardTheme {
        WorkEventFormCard(
            modifier = Modifier,
            onButtonClick = {},
            onEventStateChange = {}
        )
    }
}
