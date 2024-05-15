package sk.potociarm.workguard.ui.events

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import sk.potociarm.workguard.TIMEOUT_MILLIS
import sk.potociarm.workguard.data.workevent.WorkEventsRepository
import sk.potociarm.workguard.data.worktag.WorkTagsRepository
import sk.potociarm.workguard.ui.tags.WorkTagUiList
import sk.potociarm.workguard.ui.tags.toWorkTagState

open class WorkEventEntryViewModel(
    private val workTagsRepository: WorkTagsRepository,
    private val workEventsRepository: WorkEventsRepository
) : ViewModel() {
    open val allTagsUiState: StateFlow<WorkTagUiList> =
        workTagsRepository.getAllWorkTagsStream().map {
            WorkTagUiList(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = WorkTagUiList()
        )

    /**
     * Holds current workTag ui state
     */
    var eventState by mutableStateOf(WorkEventState())
        protected set

    open suspend fun saveWorkEvent() {
        workEventsRepository.insertWorkEvent(eventState.toWorkEvent())
    }

    suspend fun setPriceByWorkEvent() {
        if (eventState.tagId != null) {
            val tag = workTagsRepository.getWorkTagStream(eventState.tagId!!)
                .filterNotNull()
                .first()
                .toWorkTagState()
            eventState = eventState.copy(
                price = tag.price
            )
        }
    }

    fun updateUiState(newWorkEventState: WorkEventState) {
        eventState = newWorkEventState
        //newWorkEventState.validate()
    }

}
