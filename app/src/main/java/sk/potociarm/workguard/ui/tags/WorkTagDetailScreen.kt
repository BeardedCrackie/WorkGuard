package sk.potociarm.workguard.ui.tags

import androidx.annotation.StringRes
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import sk.potociarm.workguard.R
import sk.potociarm.workguard.data.WorkTag
import sk.potociarm.workguard.ui.navigation.NavDestination
import sk.potociarm.workguard.ui.theme.WorkGuardTheme

object WorkTagDetailsDestination : NavDestination {
    override val route = "worktag_details"
    override val titleRes = R.string.worktag_detail_title
    const val workTagIdArg = "id"
    val routeWithArgs = "$route/{$ WorkTagIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  WorkTagDetailsScreen(
    navigateToEditWorkTag: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
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
            workTagDetailsUiState = WorkTagUiState(),
            onDelete = { },
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
    workTagDetailsUiState: WorkTagUiState,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

         WorkTagDetailCard(
            tag =  workTagDetailsUiState.workTagDetails.toWorkTag(),
            modifier = Modifier.fillMaxWidth()
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
    tag: WorkTag, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
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
    }
}

@Composable
private fun TagDetailsRow(
    @StringRes labelResID: Int, tagDetail: String, modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(stringResource(labelResID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = tagDetail, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun WorkTagDetailsPreview() {
    WorkTagDetailCard(WorkTag(1, null,"Sample", 0.0))
}


@Preview(showBackground = true)
@Composable
fun ItemDetailsScreenPreview() {
    WorkGuardTheme {
        WorkTagDetailsBody(
            WorkTagUiState(
                workTagDetails = WorkTagDetails(
                    id = 0,
                    name = "TagName",
                    price = "0.0",
                    parent = "",
                )
            ),
            onDelete = {}
        )
    }
}
