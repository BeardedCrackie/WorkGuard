
package sk.potociarm.workguard.ui.tags

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import sk.potociarm.workguard.R
import sk.potociarm.workguard.WorkGuardTopAppBar
import sk.potociarm.workguard.data.worktag.WorkTag
import sk.potociarm.workguard.ui.AppViewModelProvider
import sk.potociarm.workguard.ui.component.WorkTagCard
import sk.potociarm.workguard.ui.navigation.NavDestination
import sk.potociarm.workguard.ui.theme.WorkGuardTheme

object WorkTagListDestination : NavDestination {
    override val route = "tag_list"
    override val titleRes = R.string.app_name
}

/**
 * Entry route for WorkTagList screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkTagListScreen(
    navigateToWorkTagEntry: () -> Unit,
    navigateToWorkTagUpdate: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkTagListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val workTagListUiState by viewModel.tagListUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            WorkGuardTopAppBar(
                title = stringResource(WorkTagEntryDestination.titleRes),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToWorkTagEntry,
                shape = MaterialTheme.shapes.medium,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_work_tag)
                )
            }
        },
    ) { innerPadding ->
        WorkTagBody(
            itemList = workTagListUiState.tagList,
            onItemClick = navigateToWorkTagUpdate,
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
        )
    }
}

@Composable
private fun WorkTagBody(
    itemList: List<WorkTag>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (itemList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_tag_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            WorkTagList(
                itemList = itemList,
                onItemClick = { onItemClick(it.id) },
                contentPadding = contentPadding,
            )
        }
    }
}

@Composable
private fun WorkTagList(
    itemList: List<WorkTag>,
    onItemClick: (WorkTag) -> Unit,
    contentPadding: PaddingValues,
) {
    LazyColumn(
        contentPadding = contentPadding,
    ) {
        items(items = itemList, key = { it.id }) { item ->
            WorkTagCard(
                tag = item.toWorkTagUi(),
                parentTag = itemList.find { it.id == item.parentId },
                modifier = Modifier
                    .clickable { onItemClick(item) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    WorkGuardTheme {
        WorkTagBody(
            sampleTagUiList(),
            onItemClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun WorkTagEmptyListPreview() {
    WorkGuardTheme {
        WorkTagBody(
            listOf(),
            onItemClick = {})
    }
}
