package sk.potociarm.workguard.ui.tags

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import sk.potociarm.workguard.R
import sk.potociarm.workguard.WorkGuardTopAppBar
import sk.potociarm.workguard.data.WorkTag
import sk.potociarm.workguard.ui.AppViewModelProvider
import sk.potociarm.workguard.ui.component.RowDescUiComponent
import sk.potociarm.workguard.ui.component.WorkTagCard
import sk.potociarm.workguard.ui.navigation.NavDestination
import sk.potociarm.workguard.ui.theme.WorkGuardTheme

object WorkTagDetailsDestination : NavDestination {
    override val route = "work_tag_details"
    override val titleRes = R.string.worktag_detail_title
    const val ID_ARG = "id"
    val routeWithArgs = "$route/{$ID_ARG}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkTagDetailsScreen(
    navigateToEditWorkTag: (Int) -> Unit,
    navigateTParentWorkTag: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkTagDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val tagUiState = viewModel.uiState.collectAsState()
    val parentTagUiState = viewModel.uiParentState.collectAsState()
    Scaffold(
        topBar = {
            WorkGuardTopAppBar(
                title = stringResource(WorkTagDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                //modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                onClick = {
                    //todo
                },
            ) {}
        }, modifier = modifier
    ) { innerPadding ->
        WorkTagDetailsBody(
            workTagDetailsUiState = tagUiState.value,
            parentWorkTagDetailsUiState = parentTagUiState.value,
            onDelete = { },
            navigateTParentWorkTag = navigateTParentWorkTag,
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

@Composable
private fun WorkTagDetailsBody(
    workTagDetailsUiState: WorkTagDetailsUiState,
    parentWorkTagDetailsUiState: WorkTagDetailsUiState,
    onDelete: () -> Unit,
    navigateTParentWorkTag: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

        WorkTagDetailCard(
            tag = workTagDetailsUiState.workTagDetails.toWorkTag(),
            parentTag = parentWorkTagDetailsUiState.workTagDetails.toWorkTag(),
            //modifier = Modifier.fillMaxWidth(),
            navigateToParentWorkTag = navigateTParentWorkTag
        )
        /*
        Button(
            onClick = onSell WorkTag,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            enabled = true
        ) {
            Text(stringResource(R.string.sell))
        */
    }
}

@Composable
fun WorkTagDetailCard(
    navigateToParentWorkTag: (Int) -> Unit,
    tag: WorkTag,
    parentTag: WorkTag,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
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
            ), Color.Black
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier.padding(all = dimensionResource(R.dimen.padding_medium)),
        ) {
            RowDescUiComponent(
                labelResID = R.string.tag_name,
                textValue = tag.name,
            )
            RowDescUiComponent(
                labelResID = R.string.tag_price,
                textValue = tag.price.toString(),
            )
            HorizontalDivider(
                color  = MaterialTheme.colorScheme.outlineVariant,
                thickness = 1.dp
            )
            RowDescUiComponent(
                labelResID = R.string.tag_parent,
                null
            )
            WorkTagCard(
                tag = parentTag,
                parentTag = null,
                modifier = modifier.clickable {
                    navigateToParentWorkTag(parentTag.id)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkTagDetailsPreview() {
    WorkTagDetailCard(
        navigateToParentWorkTag = { },
        tag = WorkTag(1, 3, "Sample", 0.0),
        parentTag = WorkTag(2, null, "Sample parent", 10.0),
    )
}


@Preview(showBackground = true)
@Composable
fun WorkDetailsScreenPreview() {
    WorkGuardTheme {
        WorkTagDetailsBody(
            WorkTagDetailsUiState(
                workTagDetails = WorkTagDetails(
                    id = 1,
                    name = "Tag-Name",
                    price = "10.0",
                    parent = "",
                )
            ),
            onDelete = {},
            navigateTParentWorkTag = {},
            parentWorkTagDetailsUiState = WorkTagDetailsUiState(
                workTagDetails = WorkTagDetails(
                    id = 0,
                    name = "Parent-Tag-Name",
                    price = "12.0",
                    parent = "",
                )
            )
        )
    }
}
