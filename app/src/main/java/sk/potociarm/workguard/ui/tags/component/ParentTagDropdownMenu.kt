package sk.potociarm.workguard.ui.tags.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import sk.potociarm.workguard.R
import sk.potociarm.workguard.data.worktag.WorkTag
import sk.potociarm.workguard.ui.tags.WorkTagUi
import sk.potociarm.workguard.ui.tags.sampleTagList
import sk.potociarm.workguard.ui.tags.sampleTagUiWithParent
import sk.potociarm.workguard.ui.tags.sampleTagUiWithoutParent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentDropdownMenu(
    modifier: Modifier = Modifier,
    tag: WorkTagUi,
    allTag: List<WorkTag>,
    startExpanded: Boolean = false,
) {
    var expanded by remember { mutableStateOf(startExpanded) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        ) {
        OutlinedTextField(
            modifier = modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
            readOnly = true,
            label = { Text(stringResource(id = R.string.tag_parent)) },
            textStyle = MaterialTheme.typography.headlineSmall,
            onValueChange = {},
            value = allTag.find { it.id == tag.parentId }?.name
                ?: stringResource(id = R.string.no_tag_parent),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                //text = { Text(workTag.name) },
                text = {
                    Text(stringResource(id = R.string.no_tag_parent))
                },
                onClick = {
                    tag.parentId = null
                    expanded = false
                },
                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
            )
            allTag.forEach { currentTag ->
                DropdownMenuItem(
                    //text = { Text(workTag.name) },
                    text = {
                        Text(currentTag.name)
                    },
                    onClick = {
                        tag.parentId = currentTag.id
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}


@Composable
fun WorkTagEditCard(
    tag: WorkTagUi,
    allTag: List<WorkTag>,
    modifier: Modifier = Modifier,
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
            var pastedName by remember { mutableStateOf(tag.name) }
            var pastedPrice by remember { mutableStateOf(tag.price.toString()) }
            OutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = pastedName,
                textStyle = MaterialTheme.typography.headlineSmall,
                onValueChange = {
                    pastedName = it
                    tag.name = it},
                isError = pastedPrice == "",
                label = { Text(stringResource(id = R.string.workTag_name_req)) }
            )
            OutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = pastedPrice, //todo currency
                textStyle = MaterialTheme.typography.headlineSmall,
                onValueChange = {
                    pastedPrice = it
                    tag.price = it //todo check if it is double
                },
                trailingIcon = {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.euro),
                        contentDescription = stringResource(R.string.back_button)
                    )
                },
                isError = pastedPrice.toDoubleOrNull() == null,
                label = { Text(stringResource(id = R.string.tag_price)) }
            )
            ParentDropdownMenu(tag = tag, allTag = allTag, modifier = modifier.fillMaxWidth(),)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ParentDropdownMenuPreview() {
    ParentDropdownMenu(
        tag = sampleTagUiWithParent(),
        allTag = sampleTagList(),
        startExpanded = true
    )
}

@Preview(showBackground = true)
@Composable
fun WorkTagEditPreview() {
    WorkTagEditCard(
        tag = sampleTagUiWithParent(),
        allTag = sampleTagList(),
    )
}

@Preview(showBackground = true)
@Composable
fun WorkTagEditWithoutParentPreview() {
    WorkTagEditCard(
        tag = sampleTagUiWithoutParent(),
        allTag = sampleTagList()
    )
}