package sk.potociarm.workguard.ui.tags

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import sk.potociarm.workguard.R
import sk.potociarm.workguard.data.WorkTag
import sk.potociarm.workguard.ui.AppViewModelProvider
import sk.potociarm.workguard.ui.navigation.NavDestination
import sk.potociarm.workguard.ui.theme.WorkGuardTheme

object WorkTagDetailsDestination : NavDestination {
    override val route = "worktag_details"
    override val titleRes = R.string.worktag_detail_title
    const val ID_ARG = "id"
    val routeWithArgs = "$route/{$ID_ARG}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  WorkTagDetailsScreen(
    navigateToEditWorkTag: (Int) -> Unit,
    navigateTParentWorkTag: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkTagDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val tagUiState = viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            /*
            InventoryTopAppBar(

                title = stringResource( WorkTagDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
             */
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
    onDelete: () -> Unit,
    navigateTParentWorkTag: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

        WorkTagDetailCard(
            tag = workTagDetailsUiState.workTagDetails.toWorkTag(),
            modifier = Modifier.fillMaxWidth(),
            navigateTParentWorkTag = navigateTParentWorkTag
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
    navigateTParentWorkTag: (Int) -> Unit,
    tag: WorkTag, modifier: Modifier = Modifier
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
            ), Color.Black),
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = modifier) {
            Row {
                Column {
                    TagDetailsRow(
                        labelResID = R.string.tag,
                        tagDetail = tag.name,
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(
                                id = R.dimen
                                    .padding_medium
                            )
                        )
                    )
                    val parent = if (tag.parentId == null) "No parent" else ""
                    TagDetailsRow(
                        labelResID = R.string.parentTtag,
                        tagDetail = parent,
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(
                                id = R.dimen
                                    .padding_medium
                            )
                        )
                    )
                }
                TagDetailsRow(
                    labelResID = R.string.tagPrice,
                    tagDetail = tag.price.toString(),
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(
                            id = R.dimen
                                .padding_medium
                        )
                    )
                )
            }
            if (tag.parentId != null) {
                WorkTagCard(
                    tag = WorkTag(1, null, "Parent Example", 10.0  ),
                    modifier = modifier.clickable {
                        navigateTParentWorkTag(tag.parentId.toInt())
                    })
            }
        }
    }
}

@Composable
private fun TagDetailsRow(
    @StringRes labelResID: Int, tagDetail: String, modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            stringResource(labelResID),
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = tagDetail,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WorkTagDetailsPreview() {
    WorkTagDetailCard(
        navigateTParentWorkTag = { },
        tag = WorkTag(1, null,"Sample", 0.0)
    )
}


@Preview(showBackground = true)
@Composable
fun ItemDetailsScreenPreview() {
    WorkGuardTheme {
        WorkTagDetailsBody(
            WorkTagDetailsUiState(
                workTagDetails = WorkTagDetails(
                    id = 0,
                    name = "TagName",
                    price = "0.0",
                    parent = "",
                )
            ),
            onDelete = {},
            navigateTParentWorkTag = {}
        )
    }
}
