package sk.potociarm.workguard.ui.tags

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import sk.potociarm.workguard.HOUR_RATE_SYMBOL
import sk.potociarm.workguard.R
import sk.potociarm.workguard.WorkGuardTopAppBar
import sk.potociarm.workguard.ui.AppViewModelProvider
import sk.potociarm.workguard.ui.component.RowDescUiComponent
import sk.potociarm.workguard.ui.navigation.NavDestination
import sk.potociarm.workguard.ui.tags.component.WorkTagCard
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
    navigateToParentWorkTag: (Int) -> Unit,
    navigateBack: () -> Unit,
    viewModel: WorkTagDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val tagUiState = viewModel.uiState.collectAsState()
    val parentTagUiState = viewModel.uiParentState.collectAsState()
    Scaffold(
        topBar = {
            WorkGuardTopAppBar(
                title = stringResource(WorkTagDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditWorkTag(tagUiState.value.id) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateEndPadding(LocalLayoutDirection.current)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.work_tag_edit),
                )
            }
        },
    ) { innerPadding ->
        WorkTagDetailsBody(
            tagUiState = tagUiState.value,
            parentTagUiState = parentTagUiState.value,
            navigateTParentWorkTag = navigateToParentWorkTag,
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
    tagUiState: WorkTagUi,
    parentTagUiState: WorkTagUi?,
    navigateTParentWorkTag: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),

        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {

        WorkTagDetailCard(
            tag = tagUiState,
            parentTag = parentTagUiState,
            navigateToParentWorkTag = navigateTParentWorkTag
        )
    }
}

@Composable
fun WorkTagDetailCard(
    navigateToParentWorkTag: (Int) -> Unit,
    tag: WorkTagUi,
    parentTag: WorkTagUi?,
    modifier: Modifier = Modifier
) {
    Card(
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
                textValue = "${tag.price} $HOUR_RATE_SYMBOL",
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                thickness = 1.dp
            )
            RowDescUiComponent(
                labelResID = R.string.tag_parent,
                null
            )
            if (tag.parentId != null) {
                WorkTagCard(
                    tag = parentTag!!, //if parentId is null, then parent does not exists
                    parentTag = null,
                    modifier = Modifier.clickable {
                        navigateToParentWorkTag(parentTag.id)
                    }
                )
            } else {
                Text(
                    text = stringResource(id = R.string.no_tag_parent),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkTagDetailsPreview() {
    WorkTagDetailCard(
        navigateToParentWorkTag = { },
        tag = sampleTagUiWithParent(),
        parentTag = sampleTagUiWithParent(),
    )
}

@Preview(showBackground = true)
@Composable
fun WorkTagDetailsNoParentPreview() {
    WorkTagDetailCard(
        navigateToParentWorkTag = { },
        tag = sampleTagUiWithoutParent(),
        parentTag = null,
    )
}


@Preview(showBackground = true)
@Composable
fun WorkDetailsScreenPreview() {
    WorkGuardTheme {
        WorkTagDetailsBody(
            tagUiState = sampleTagUiWithParent(),
            parentTagUiState = sampleTagUiWithoutParent(),
            navigateTParentWorkTag = {},
        )
    }
}
