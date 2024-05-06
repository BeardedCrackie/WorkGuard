package sk.potociarm.workguard.ui.tags.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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

@Composable
fun WorkTagEditCard(
    tag: WorkTagUi,
    allTag: List<WorkTag>,
    modifier: Modifier = Modifier,
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
            var pastedName by remember { mutableStateOf(tag.name) }
            if (pastedName == "") pastedName = "Tag Name"
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