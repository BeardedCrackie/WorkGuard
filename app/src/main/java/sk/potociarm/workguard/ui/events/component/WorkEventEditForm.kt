package sk.potociarm.workguard.ui.events.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.WorkGuardTheme
import sk.potociarm.workguard.R
import sk.potociarm.workguard.ui.events.WorkEventState

@Composable
fun WorkEventForm(
    modifier: Modifier = Modifier,
    initialWorkEvent: WorkEventState = WorkEventState(),
    onEventStateChange: (WorkEventState) -> Unit,
    onButtonClick: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(all = dimensionResource(id = R.dimen.padding_small)),
    ) {
    var workEvent by remember { mutableStateOf(initialWorkEvent) }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier.padding(all = dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            // Title field
            WorkEventForm(
                workEvent,
                onEventStateChange
            )

            Button(
                onClick = { onButtonClick() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}

@Composable
private fun WorkEventForm(
    workEvent: WorkEventState,
    onEventStateChange: (WorkEventState) -> Unit
) {
    OutlinedTextField(
        value = workEvent.name,
        onValueChange = {
            onEventStateChange(workEvent.copy(name = it))
        },
        label = { Text("Title") },
        modifier = Modifier.fillMaxWidth(),
    )

    // Description field
    OutlinedTextField(
        value = workEvent.description,
        onValueChange = {
            onEventStateChange(workEvent.copy(description = it))
        },
        label = { Text("Description") },
        modifier = Modifier.fillMaxWidth()
    )

    // Date field
    OutlinedTextField(
        value = workEvent.date.toString(),
        onValueChange = { /*todo*/ },
        label = { Text("date") }, //todo set do String resource
        modifier = Modifier.fillMaxWidth()
    )

    // Start time field
    OutlinedTextField(
        value = workEvent.startTime.toString(),
        onValueChange = { /*todo*/ },
        label = { Text("start time") }, //todo set do String resource
        modifier = Modifier.fillMaxWidth()
    )

    // End time field
    OutlinedTextField(
        value = workEvent.endTime.toString(),
        onValueChange = { /*todo*/ },
        label = { Text("end time") }, //todo set do String resource
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
    )
}

@Preview(showBackground = true)
@Composable
fun WorkEntryScreenPreview() {
    WorkGuardTheme {
        WorkEventForm(
            modifier = Modifier,
            onButtonClick = {},
            onEventStateChange = {}
        )
    }
}