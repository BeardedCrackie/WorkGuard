package sk.potociarm.workguard.ui.tags

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import sk.potociarm.workguard.data.worktag.WorkTagsRepository

class WorkTagEntryViewModel(private val workTagsRepository: WorkTagsRepository) : ViewModel() {
    /**
     * Holds current workTag ui state
     */
    var workTagUiState by mutableStateOf(WorkTagUi())
        private set

    fun updateUiState(workTagDetails: WorkTagUi) {
        workTagUiState = workTagDetails
    }

    suspend fun saveWorkTag() {
        workTagsRepository.insertWorkTag(workTagUiState.toWorkTag())
    }
}
