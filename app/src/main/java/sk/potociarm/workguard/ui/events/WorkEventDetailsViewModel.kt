package sk.potociarm.workguard.ui.events

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import sk.potociarm.workguard.TIMEOUT_MILLIS
import sk.potociarm.workguard.data.workevent.WorkEventsRepository
import sk.potociarm.workguard.ui.tags.WorkTagState
import sk.potociarm.workguard.ui.tags.toWorkTagUi

class WorkEventDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    workEventsRepository: WorkEventsRepository
) : ViewModel() {
    private val itemId: Int = checkNotNull(savedStateHandle[WorkEventDetailsDestination.ID_ARG])

    val eventWithTagState: StateFlow<WorkEventDetailUiState> =
        workEventsRepository.getUsersAndLibraries(itemId)
            .filterNotNull()
            .map {
                WorkEventDetailUiState(
                    tag = it.tag.toWorkTagUi(),
                    event = it.event.toWorkEventState()
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = WorkEventDetailUiState()
            )

    /**
     * UI state for WorkEventWithTagState
     */
    data class WorkEventDetailUiState(
        val tag: WorkTagState = WorkTagState(),
        val event: WorkEventState = WorkEventState()
    )

}
