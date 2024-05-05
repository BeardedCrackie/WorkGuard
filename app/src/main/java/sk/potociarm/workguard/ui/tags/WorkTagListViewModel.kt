package sk.potociarm.workguard.ui.tags

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import sk.potociarm.workguard.data.worktag.WorkTagsRepository

class WorkTagListViewModel(workTagsRepository: WorkTagsRepository) : ViewModel() {
    val tagListUiState: StateFlow<WorkTagUiList> =
        workTagsRepository.getAllWorkTagsStream().map {
            WorkTagUiList(it)
        }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = WorkTagUiList()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
