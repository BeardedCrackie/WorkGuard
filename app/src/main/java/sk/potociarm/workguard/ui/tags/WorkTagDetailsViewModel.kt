package sk.potociarm.workguard.ui.tags

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import sk.potociarm.workguard.data.WorkTagsRepository

class WorkTagDetailsViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val itemId: Int = checkNotNull(savedStateHandle[WorkTagDetailsDestination.WorkTagIdArg])

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
