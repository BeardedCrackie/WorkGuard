package sk.potociarm.workguard.ui.events

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sk.potociarm.workguard.TIMEOUT_MILLIS
import sk.potociarm.workguard.data.workevent.WorkEventsRepository
import sk.potociarm.workguard.data.worktag.WorkTagsRepository
import sk.potociarm.workguard.ui.tags.WorkTagUiList

class WorkEventEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val workTagsRepository: WorkTagsRepository,
    private val workEventsRepository: WorkEventsRepository
) : WorkEventEntryViewModel(workTagsRepository, workEventsRepository) {

    private val eventId: Int = checkNotNull(savedStateHandle[WorkEventEditDestination.ID_ARG])

    override val otherTagsUiState: StateFlow<WorkTagUiList> =
        workTagsRepository.getOtherWorkTagsStream(eventState.id).map {
            WorkTagUiList(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = WorkTagUiList()
        )

    init {
        viewModelScope.launch {
            eventState = workEventsRepository.getWorkEventStream(eventId)
                .filterNotNull()
                .first()
                .toWorkEventState()
        }
    }

    suspend fun deleteEvent() {
        workEventsRepository.deleteWorkEventById(eventState.id)
    }

    override suspend fun saveWorkEvent() {
        Log.v("WorkEvent save",eventState.toString())
        workEventsRepository.updateWorkEvent(eventState.toWorkEvent())
    }

}

