package sk.potociarm.workguard.ui.tags.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import sk.potociarm.workguard.HOUR_RATE_SYMBOL
import sk.potociarm.workguard.R
import sk.potociarm.workguard.data.worktag.WorkTag
import sk.potociarm.workguard.ui.tags.WorkTagState
import sk.potociarm.workguard.ui.tags.sampleTagList
import sk.potociarm.workguard.ui.tags.sampleTagUiWithParent
import sk.potociarm.workguard.ui.tags.sampleTagUiWithoutParent
import java.text.NumberFormat

@Composable
fun WorkTagForm(
    tag: WorkTagState,
    onTagStateChange: (WorkTagState) -> Unit,
    otherTags: List<WorkTag>,
    modifier: Modifier = Modifier,
) {
        Column(
        ) {
            //var pastedName by remember { mutableStateOf(tag.name) }
            //var pastedPrice by remember { mutableStateOf(tag.price) }
            OutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = tag.name,
                textStyle = MaterialTheme.typography.headlineSmall,
                onValueChange = {
                    onTagStateChange(tag.copy(name = it))
                },
                isError = tag.name == "",
                label = { Text(stringResource(id = R.string.workTag_name_req)) }
            )
            OutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = tag.price,
                textStyle = MaterialTheme.typography.headlineSmall,
                onValueChange = {
                    onTagStateChange(tag.copy(price = it))
                },
                suffix = {
                    NumberFormat.getNumberInstance().currency?.let {
                        Text(HOUR_RATE_SYMBOL, style = MaterialTheme.typography.headlineSmall)
                         }},
                /*
                trailingIcon = {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.euro),
                        contentDescription = stringResource(R.string.back_button)
                    )
                },
                */
                isError = tag.price.toDoubleOrNull() == null,
                label = { Text(stringResource(id = R.string.tag_price)) }
            )
            TagDropdownMenu(tag = tag, onTagStateChange = onTagStateChange,allTag = otherTags, modifier = modifier.fillMaxWidth())
        }
}

@Composable
fun WorkTagFormCard(
    modifier: Modifier = Modifier,
    tagUiState: WorkTagState,
    onTagStateChange: (WorkTagState) -> Unit,
    otherTags: List<WorkTag>,
    onButtonClick: () -> Unit,
    buttonLabelResource: Int = R.string.add_work_tag
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
    Column(
        //modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        modifier.padding(all = dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        WorkTagForm(
            tag = tagUiState,
            otherTags = otherTags,
            onTagStateChange = onTagStateChange
        )
        OutlinedButton(
            onClick = { onButtonClick() },
            enabled = tagUiState.valid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(buttonLabelResource))
        }
    }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkTagEditPreview() {
    WorkTagForm(
        tag = sampleTagUiWithParent(),
        otherTags = sampleTagList(),
        onTagStateChange = {}
    )
}

@Preview(showBackground = true)
@Composable
fun WorkTagEditBodyPreview() {
    WorkTagFormCard(
        onButtonClick = {},
        onTagStateChange = {},
        tagUiState = sampleTagUiWithoutParent(),
        otherTags = sampleTagList(),
        buttonLabelResource = R.string.save_action
    )
}