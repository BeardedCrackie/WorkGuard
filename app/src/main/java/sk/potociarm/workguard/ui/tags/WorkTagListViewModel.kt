package sk.potociarm.workguard.ui.tags

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import sk.potociarm.workguard.data.WorkTag
import sk.potociarm.workguard.data.WorkTagsRepository

class WorkTagListViewModel(workTagsRepository: WorkTagsRepository) : ViewModel() {
    val workTagListUiState: StateFlow<WorkTagListUiState> =
        workTagsRepository.getAllWorkTagsStream().map {
            WorkTagListUiState(it)
        }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = WorkTagListUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Ui State for HomeScreen
 */
data class WorkTagListUiState(val itemList: List<WorkTag> = listOf())