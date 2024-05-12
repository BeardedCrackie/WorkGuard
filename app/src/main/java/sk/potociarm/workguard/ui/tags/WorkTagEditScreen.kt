package sk.potociarm.workguard.ui.tags

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import sk.potociarm.workguard.R
import sk.potociarm.workguard.WorkGuardTopAppBar
import sk.potociarm.workguard.ui.AppViewModelProvider
import sk.potociarm.workguard.ui.navigation.NavDestination
import sk.potociarm.workguard.ui.tags.component.WorkTagFormCard
import sk.potociarm.workguard.ui.theme.WorkGuardTheme

object WorkTagEditDestination : NavDestination {
    override val route = "work_tag_edits"
    override val titleRes = R.string.worktag_detail_title
    const val ID_ARG = "id"
    val routeWithArgs = "$route/{$ID_ARG}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkTagEditScreen(
    navigateBack: () -> Unit,
    onDelete: () -> Unit,
    viewModel: WorkTagEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val tagListState by viewModel.otherTagsUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            WorkGuardTopAppBar(
                title = stringResource(WorkTagDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
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
        },
    ) { innerPadding ->
        WorkTagFormCard(
            tagUiState = viewModel.tagState,
            onTagStateChange = viewModel::updateUiState,
            otherTags = tagListState.tagList,
            onButtonClick = {
                coroutineScope.launch {
                    viewModel.saveWorkTag()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
        )
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    coroutineScope.launch {
                        viewModel.deleteTag()
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
fun WorkEditScreenPreview() {
    WorkGuardTheme {
        WorkTagFormCard(
            modifier = Modifier,
            tagUiState = sampleTagUiWithoutParent(),
            onTagStateChange = {},
            onButtonClick = {},
            otherTags = sampleTagList(),
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        })
}

