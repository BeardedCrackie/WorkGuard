
package sk.potociarm.workguard.ui.tags

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import sk.potociarm.workguard.R
import sk.potociarm.workguard.WorkGuardTopAppBar
import sk.potociarm.workguard.data.WorkTag
import sk.potociarm.workguard.ui.AppViewModelProvider
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
    val workTagListUiState by viewModel.workTagListUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            WorkGuardTopAppBar(
                title = stringResource(WorkTagEntryDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToWorkTagEntry,
                shape = MaterialTheme.shapes.medium,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.tag)
                )
            }
        },
    ) { innerPadding ->
        WorkTagBody(
            itemList = workTagListUiState.itemList,
            onItemClick = navigateToWorkTagUpdate,
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
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
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun WorkTagList(
    itemList: List<WorkTag>,
    onItemClick: (WorkTag) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = itemList, key = { it.id }) { item ->
            WorkTagCard(
                tag = item,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(item) })
        }
    }
}


@Composable
fun WorkTagCard(
    tag: WorkTag, modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(
                id = R.dimen
                    .elevation
            )
        ),
        border = BorderStroke(
            dimensionResource(
                id = R.dimen
                    .borderSize
            ), Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row {
            Column {
                Text(
                    modifier = modifier,
                    text = tag.name,
                    style = MaterialTheme.typography.headlineMedium
                )
                if (tag.parentId != null) {
                    Text(
                        modifier = modifier,
                        text = "Parent name", //todo parent name
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = modifier,
                text = tag.price.toString() + " €/h", //todo currency
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    WorkGuardTheme {
        WorkTagBody(listOf(
            WorkTag(1, 1, "Tag 1",10.0),
            WorkTag(2, 1,"Tag 2", 20.0),
            WorkTag(3, null, "Tag 3", 200.0),
        ), onItemClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun WorkTagEmptyListPreview() {
    WorkGuardTheme {
        WorkTagBody(listOf(), onItemClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun WorkTagItemPreview() {
    WorkGuardTheme {
        WorkTagDetailCard(
            tag = WorkTag(1, 2, "Tag 1",10.0),
            navigateTParentWorkTag = {}
        )
    }
}
