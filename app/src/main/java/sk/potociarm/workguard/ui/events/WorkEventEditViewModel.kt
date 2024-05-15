package sk.potociarm.workguard.ui.events

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import sk.potociarm.workguard.data.workevent.WorkEventsRepository
import sk.potociarm.workguard.data.worktag.WorkTagsRepository

class WorkEventEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val workTagsRepository: WorkTagsRepository,
    private val workEventsRepository: WorkEventsRepository
) : WorkEventEntryViewModel(workTagsRepository, workEventsRepository) {

    private val eventId: Int = checkNotNull(savedStateHandle[WorkEventEditDestination.ID_ARG])

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

