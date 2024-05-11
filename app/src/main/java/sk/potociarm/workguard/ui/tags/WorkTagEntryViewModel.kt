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
import sk.potociarm.workguard.TIMEOUT_MILLIS
import sk.potociarm.workguard.data.worktag.WorkTagsRepository

open class WorkTagEntryViewModel(private val workTagsRepository: WorkTagsRepository) : ViewModel() {

    open val otherTagsUiState: StateFlow<WorkTagUiList> =
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
    var tagState by mutableStateOf(WorkTagState())
        protected set

    open suspend fun saveWorkTag() {
        workTagsRepository.insertWorkTag(tagState.toWorkTag())
    }

    fun updateUiState(newWorkTagState: WorkTagState) {
        tagState = newWorkTagState
        newWorkTagState.validate()
    }

}
