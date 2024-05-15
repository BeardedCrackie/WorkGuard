package sk.potociarm.workguard.ui.events

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.WorkGuardTheme
import kotlinx.coroutines.launch
import sk.potociarm.workguard.R
import sk.potociarm.workguard.WorkGuardTopAppBar
import sk.potociarm.workguard.ui.AppViewModelProvider
import sk.potociarm.workguard.ui.component.DeleteConfirmationDialog
import sk.potociarm.workguard.ui.events.component.WorkEventFormCard
import sk.potociarm.workguard.ui.navigation.NavDestination
import sk.potociarm.workguard.ui.tags.sampleTagList

object WorkEventEditDestination : NavDestination {
    override val route = "work_event_edits"
    override val titleRes = R.string.worktag_detail_title
    const val ID_ARG = "id"
    val routeWithArgs = "$route/{$ID_ARG}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkEventEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    onDelete: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: WorkEventEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val tagListState by viewModel.allTagsUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            WorkGuardTopAppBar(
                title = stringResource(WorkEventEditDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp,
                actions = {
                    IconButton(
                        onClick = {
                            deleteConfirmationRequired = true
                        }) {
                        Icon(
                            ImageVector.vectorResource(id = R.drawable.delete),
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        WorkEventFormCard(
            workEventState = viewModel.eventState,
            allTagsState = tagListState.tagList,
            onEventStateChange = viewModel::updateUiState,
            onButtonClick = {
                coroutineScope.launch {
                    viewModel.saveWorkEvent()
                    navigateBack()
                }
            },
            contentPadding = innerPadding,
            onEventPriceChange = {
                coroutineScope.launch {
                    viewModel.setPriceByWorkEvent()
                }
            },
        )
        if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                coroutineScope.launch {
                    viewModel.deleteEvent()
                    onDelete()
                }
            },
            onDeleteCancel = {
                deleteConfirmationRequired = false
            },
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        )
    }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkEventEditScreenPreview() {
    WorkGuardTheme {
        WorkEventFormCard(
            modifier = Modifier,
            workEventState = sampleEventWithoutTag(),
            allTagsState = sampleTagList(),
            onEventStateChange = {},
            onButtonClick = {},
            onEventPriceChange = {}
        )
    }
}
