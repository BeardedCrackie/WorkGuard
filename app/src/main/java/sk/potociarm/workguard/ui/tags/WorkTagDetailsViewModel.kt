package sk.potociarm.workguard.ui.tags

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class WorkTagDetailsViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val itemId: Int = checkNotNull(savedStateHandle[WorkTagDetailsDestination.workTagIdArg])

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
