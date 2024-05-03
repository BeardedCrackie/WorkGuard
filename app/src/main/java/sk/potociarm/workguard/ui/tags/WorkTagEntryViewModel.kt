package sk.potociarm.workguard.ui.tags

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import sk.potociarm.workguard.data.WorkTagsRepository

class WorkTagEntryViewModel(private val workTagsRepository: WorkTagsRepository) : ViewModel() {
    /**
     * Holds current workTag ui state
     */
    var workTagUiState by mutableStateOf(WorkTagUiState())
        private set

    fun updateUiState(workTagDetails: WorkTagDetails) {
        workTagUiState =
            WorkTagUiState(workTagDetails = workTagDetails, isEntryValid = validateInput(workTagDetails))
    }

    private fun validateInput(uiState: WorkTagDetails = workTagUiState.workTagDetails): Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }

    suspend fun saveWorkTag() {
        if (validateInput()) {
            workTagsRepository.insertWorkTag(workTagUiState.workTagDetails.toWorkTag())
        }
    }
}
