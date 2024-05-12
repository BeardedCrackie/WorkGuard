
package sk.potociarm.workguard.ui.tags

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import sk.potociarm.workguard.R
import sk.potociarm.workguard.WorkGuardTopAppBar
import sk.potociarm.workguard.ui.AppViewModelProvider
import sk.potociarm.workguard.ui.navigation.NavDestination
import sk.potociarm.workguard.ui.tags.component.WorkTagFormCard
import sk.potociarm.workguard.ui.theme.WorkGuardTheme

object WorkTagEntryDestination : NavDestination {
    override val route = "workTag_entry"
    override val titleRes = R.string.workTag_entry_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkTagEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: WorkTagEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val tagListState by viewModel.otherTagsUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            WorkGuardTopAppBar(
                title = stringResource(WorkTagEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp,
            )
        }
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
            buttonLabelResource = R.string.add_work_tag,
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WorkEntryScreenPreview() {
    WorkGuardTheme {
        WorkTagFormCard(
            modifier = Modifier,
            tagUiState = sampleTagUiWithoutParent(),
            onTagStateChange = {},
            onButtonClick = {},
            otherTags = sampleTagList(),
            buttonLabelResource = R.string.add_work_tag,
        )
    }
}
