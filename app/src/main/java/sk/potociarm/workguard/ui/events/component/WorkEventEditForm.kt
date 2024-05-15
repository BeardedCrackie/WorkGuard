package sk.potociarm.workguard.ui.events.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.WorkGuardTheme
import kotlinx.datetime.LocalDate
import sk.potociarm.workguard.R
import sk.potociarm.workguard.ui.events.WorkEventState
import sk.potociarm.workguard.ui.events.sampleEventWithTag


@Composable
fun WorkEventFormCard(
    modifier: Modifier = Modifier,
    workEventState: WorkEventState,
    onEventStateChange: (WorkEventState) -> Unit,
    onButtonClick: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(all = dimensionResource(id = R.dimen.padding_small)),
) {
    //val workEvent by remember { mutableStateOf(workEventState) }
    Log.v("Event edit", workEventState.toString())


    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        onClick = {}
    ) {
    Column(
        modifier.padding(all = dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        // Title field
        WorkEventForm(
            workEvent = workEventState,
            onEventStateChange = onEventStateChange
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

@SuppressLint("UnrememberedMutableInteractionSource")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkEventForm(
    workEvent: WorkEventState,
    onEventStateChange: (WorkEventState) -> Unit
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

        if (openDialog.value) {
            val datePickerState = rememberDatePickerState()
            Box {
                DatePickerDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    confirmButton = {
                        Button(onClick = {
                            Log.d("Event edit","event date picked ${LocalDate.fromEpochDays(
                                (datePickerState.selectedDateMillis!! / 86400000).toInt())}")
                            openDialog.value = false
                            onEventStateChange(
                                workEvent.copy(
                                    date = LocalDate.fromEpochDays(
                                        (datePickerState.selectedDateMillis!! / 86400000).toInt()
                                    ))
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

        // Date field
        OutlinedTextField(
            value = workEvent.date.toString(),
            enabled = false,
            onValueChange = {},
            label = { Text("date") }, //todo set do String resource=
            modifier = Modifier
                .clickable {
                    openDialog.value = true
                }
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledBorderColor = MaterialTheme.colorScheme.outline,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                //For Icons
                disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant)
        )

        // Start time field
        OutlinedTextField(
            value = workEvent.startTime.toString(),
            enabled = false,
            onValueChange = {},
            label = { Text("start time") }, //todo set do String resource=
            modifier = Modifier
                .clickable {
                    openDialog.value = true
                }
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            //For Icons
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant)
        )

        // End time field
        OutlinedTextField(
            enabled = false,
            value = workEvent.endTime.toString(),
            onValueChange = { /*todo*/ },
            label = { Text("end time") }, //todo set do String resource
            modifier = Modifier
                .fillMaxWidth()
                .clickable { },
            colors = TextFieldDefaults.colors()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WorkEventFormPreview() {
    WorkEventForm(
        onEventStateChange = {},
        workEvent = sampleEventWithTag()
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
            workEventState = sampleEventWithTag()
        )
    }
}