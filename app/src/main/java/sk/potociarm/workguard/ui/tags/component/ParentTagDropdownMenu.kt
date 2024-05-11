package sk.potociarm.workguard.ui.tags.component

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import sk.potociarm.workguard.R
import sk.potociarm.workguard.data.worktag.WorkTag
import sk.potociarm.workguard.ui.tags.WorkTagState
import sk.potociarm.workguard.ui.tags.sampleTagList
import sk.potociarm.workguard.ui.tags.sampleTagUiWithParent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentDropdownMenu(
    modifier: Modifier = Modifier,
    tag: WorkTagState,
    onTagStateChange: (WorkTagState) -> Unit,
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
                    onTagStateChange(tag.copy(parentId = null))
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
                        onTagStateChange(tag.copy(parentId = currentTag.id))
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ParentDropdownMenuPreview() {
    ParentDropdownMenu(
        tag = sampleTagUiWithParent(),
        allTag = sampleTagList(),
        startExpanded = true,
        onTagStateChange = {}
    )
}

