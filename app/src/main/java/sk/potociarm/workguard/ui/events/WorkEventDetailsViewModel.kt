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
import sk.potociarm.workguard.data.worktag.WorkTagsRepository
import sk.potociarm.workguard.ui.tags.WorkTagState
import sk.potociarm.workguard.ui.tags.toWorkTagUi

class WorkEventDetailsViewModel(savedStateHandle: SavedStateHandle, workTagRepository: WorkTagsRepository, workEventsRepository: WorkEventsRepository) : ViewModel() {
    private val itemId: Int = checkNotNull(savedStateHandle[WorkEventDetailsDestination.ID_ARG])

    val eventUiState: StateFlow<WorkEventState> =
        workEventsRepository.getWorkEventStream(itemId)
            .filterNotNull()
            .map {
                it.toWorkEventState()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = WorkEventState()
            )

    val tagUiState: StateFlow<WorkTagState>? =
        eventUiState.value.tagId?.let {
            workTagRepository.getWorkTagStream(it)
                .filterNotNull()
                .map {
                    it.toWorkTagUi()
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                    initialValue = WorkTagState()
                )
        }
}
