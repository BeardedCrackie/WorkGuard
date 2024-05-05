package sk.potociarm.workguard.ui.tags

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import sk.potociarm.workguard.data.worktag.WorkTagsRepository

class WorkTagDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    workTagRepository: WorkTagsRepository
) : ViewModel() {
    private val itemId: Int = checkNotNull(savedStateHandle[WorkTagDetailsDestination.ID_ARG])

    val uiState: StateFlow<WorkTagDetailsUiState> =
        workTagRepository.getWorkTagStream(itemId)
            .filterNotNull()
            .map {
                WorkTagDetailsUiState(workTagDetails = it.toWorkTagDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = WorkTagDetailsUiState()
            )
    val uiParentState: StateFlow<WorkTagDetailsUiState> =
        workTagRepository.getWorkTagParentStream(itemId)
            .filterNotNull()
            .map {
                WorkTagDetailsUiState(workTagDetails = it.toWorkTagDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = WorkTagDetailsUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class WorkTagDetailsUiState(
    val workTagDetails: WorkTagDetails = WorkTagDetails()
)