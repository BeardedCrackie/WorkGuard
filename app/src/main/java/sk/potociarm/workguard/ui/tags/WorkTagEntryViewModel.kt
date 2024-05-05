package sk.potociarm.workguard.ui.tags

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import sk.potociarm.workguard.data.worktag.WorkTagsRepository

class WorkTagEntryViewModel(private val workTagsRepository: WorkTagsRepository) : ViewModel() {

    val allTagsUiState: StateFlow<WorkTagUiList> =
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
    var tagUiState by mutableStateOf(WorkTagUi())
        private set

    suspend fun saveWorkTag() {
        workTagsRepository.insertWorkTag(tagUiState.toWorkTag())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}
