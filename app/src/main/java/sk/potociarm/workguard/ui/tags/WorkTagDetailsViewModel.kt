package sk.potociarm.workguard.ui.tags

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import sk.potociarm.workguard.TIMEOUT_MILLIS
import sk.potociarm.workguard.data.worktag.WorkTagsRepository

class WorkTagDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    workTagRepository: WorkTagsRepository,
) : ViewModel() {
    private val itemId: Int = checkNotNull(savedStateHandle[WorkTagDetailsDestination.ID_ARG])

    val uiState: StateFlow<WorkTagState> =
        workTagRepository.getWorkTagStream(itemId)
            .filterNotNull()
            .map {
                it.toWorkTagState()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = WorkTagState()
            )

    val uiParentState: StateFlow<WorkTagState> =
        workTagRepository.getWorkTagParentStream(itemId)
            .filterNotNull()
            .map {
                it.toWorkTagState()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = WorkTagState()
            )

}
