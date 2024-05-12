package sk.potociarm.workguard.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDate
import sk.potociarm.workguard.TIMEOUT_MILLIS
import sk.potociarm.workguard.data.workevent.WorkEvent
import sk.potociarm.workguard.data.workevent.WorkEventsRepository
import sk.potociarm.workguard.data.worktag.WorkTagsRepository

class WorkEventListViewModel(
    workTagsRepository: WorkTagsRepository,
    workEventsRepository: WorkEventsRepository,
    ) : ViewModel()
{
    val eventMap: StateFlow<Map<LocalDate, List<WorkEvent>>> =
        workEventsRepository.getWorkEventsGroupedByDay()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = emptyMap()
            )

}

