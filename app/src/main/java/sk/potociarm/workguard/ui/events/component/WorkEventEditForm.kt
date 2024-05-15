package sk.potociarm.workguard.ui.events.component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.WorkGuardTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import sk.potociarm.workguard.R
import sk.potociarm.workguard.data.worktag.WorkTag
import sk.potociarm.workguard.ui.component.OutlinedLabeledButton
import sk.potociarm.workguard.ui.component.TimePickerDialog
import sk.potociarm.workguard.ui.events.WorkEventState
import sk.potociarm.workguard.ui.events.sampleEventWithTag
import sk.potociarm.workguard.ui.tags.sampleTagList


@Composable
fun WorkEventFormCard(
    modifier: Modifier = Modifier,
    workEventState: WorkEventState,
    allTagsState: List<WorkTag>,
    onEventStateChange: (WorkEventState) -> Unit,
    onButtonClick: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(all = dimensionResource(id = R.dimen.padding_small)),
) {
    //val workEvent by remember { mutableStateOf(workEventState) }
    Log.v("Event edit", workEventState.toString())
    Box(
        modifier = Modifier.padding(contentPadding)
    )
    {
        Card(
            modifier = Modifier.padding(all = dimensionResource(id = R.dimen.padding_small)),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
        ) {
            Column(
                modifier.padding(all = dimensionResource(R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
            ) {
                // Title field
                WorkEventForm(
                    workEvent = workEventState,
                    onEventStateChange = onEventStateChange,
                    allTag = allTagsState
                )
                OutlinedButton(
                    onClick = { onButtonClick() },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small,
                ) {
                    Text("Save")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkEventForm(
    workEvent: WorkEventState,
    onEventStateChange: (WorkEventState) -> Unit,
    allTag: List<WorkTag>,
) {
    Column(
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

        val openDialog = remember { mutableStateOf(false) }
        DatePickerPopup(onEventStateChange, workEvent, openDialog)


        var showStartTimePicker by remember { mutableStateOf(false) }

        val state = rememberTimePickerState()
        if (showStartTimePicker) {
            TimePickerDialog(
                onCancel = { showStartTimePicker = false },
                onConfirm = {
                    onEventStateChange(
                        workEvent.copy(
                            startTime = LocalTime(hour = state.hour, minute = state.minute)
                        )
                    )
                    showStartTimePicker = false
                },
            ) { TimePicker(state = state) }
        }

        var showEndTimePicker by remember { mutableStateOf(false) }

        //val state = rememberTimePickerState()
        if (showEndTimePicker) {
            TimePickerDialog(
                onCancel = { showEndTimePicker = false },
                onConfirm = {
                    onEventStateChange(
                        workEvent.copy(
                            endTime = LocalTime(hour = state.hour, minute = state.minute)
                        )
                    )
                    showEndTimePicker = false
                },
            ) {
                TimePicker(state = state)
            }
        }

        OutlinedLabeledButton(
            label = { Text(stringResource(R.string.work_event_start_date)) },
            value = workEvent.date.toString(),
            onButtonClick = {
                openDialog.value = true
            },
            //modifier = Modifier.weight(1f)
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Start time field
            OutlinedLabeledButton(
                label = { Text(stringResource(R.string.work_event_start_time)) },
                value = workEvent.startTime.toString(),
                onButtonClick = {
                    showStartTimePicker = true
                },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
            // End time field
            OutlinedLabeledButton(
                label = { Text(stringResource(R.string.work_event_end_time)) },
                value = workEvent.endTime.toString(),
                onButtonClick = {
                    showEndTimePicker = true
                },
                modifier = Modifier.weight(1f)
            )
        }


        /* ---------------------------- */
        var expanded by rememberSaveable { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    Log.v("Event action", "Event clicked $expanded")
                    expanded = true },
            ) {
                OutlinedTextField(
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth(),
                    readOnly = true,
                    //enabled = false,
                    label = { Text(stringResource(id = R.string.tag_parent)) },
                    //textStyle = MaterialTheme.typography.headlineSmall,
                    onValueChange = {},
                    value = allTag.find { it.id == workEvent.tagId }?.name
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
                            onEventStateChange(
                                workEvent.copy(tagId = null)
                            )
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
                                onEventStateChange(
                                    workEvent.copy(
                                        tagId = currentTag.id
                                    )
                                )
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }

        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Description field
            Switch(
                enabled = workEvent.tagId != null,
                checked = workEvent.overridePrice,
                onCheckedChange = {
                    onEventStateChange(
                        workEvent.copy(
                            overridePrice = it,
                            price = if (it) workEvent.price.toDouble() //todo set workEvent.price = tag price
                            else workEvent.price
                        )
                    )
                    Log.v("Event edit", "switch enabled: $it $workEvent")
                },
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )

            // Price field
            OutlinedTextField(
                enabled = workEvent.overridePrice,
                value = workEvent.price.toString(),
                onValueChange = {
                    onEventStateChange(workEvent.copy(price = it.toDouble()))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Price") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f)
            )
        }
    }
        /* ----------------------------- */

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DatePickerPopup(
    onEventStateChange: (WorkEventState) -> Unit,
    workEvent: WorkEventState,
    openDialog: MutableState<Boolean>
): MutableState<Boolean> {

    if (openDialog.value) {
        val datePickerState = rememberDatePickerState()
        Box {
            DatePickerDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                confirmButton = {
                    Button(onClick = {
                        Log.d(
                            "Event edit", "event date picked ${
                                LocalDate.fromEpochDays(
                                    (datePickerState.selectedDateMillis!! / 86400000).toInt()
                                )
                            }"
                        )
                        openDialog.value = false
                        onEventStateChange(
                            workEvent.copy(
                                date = LocalDate.fromEpochDays(
                                    (datePickerState.selectedDateMillis!! / 86400000).toInt()
                                )
                            )
                        )
                    }) {
                        Text("Accept")
                    }

                }) {
                DatePicker(
                    state = datePickerState,
                )
            }
        }
    }
    return openDialog
}


@Preview(showBackground = true)
@Composable
fun WorkEventFormPreview() {
    WorkEventForm(
        onEventStateChange = {},
        workEvent = sampleEventWithTag(),
        allTag = sampleTagList()
    )
}

@Preview(showBackground = true)
@Composable
fun WorkEventFormCardPreview() {
    WorkGuardTheme {
        WorkEventFormCard(
            //modifier = Modifier,
            onButtonClick = {},
            onEventStateChange = {},
            workEventState = sampleEventWithTag(),
            allTagsState = sampleTagList()
        )
    }
}