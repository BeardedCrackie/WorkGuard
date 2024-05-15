package sk.potociarm.workguard.ui.tags

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sk.potociarm.workguard.TIMEOUT_MILLIS
import sk.potociarm.workguard.data.worktag.WorkTagsRepository

class WorkTagEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val workTagsRepository: WorkTagsRepository
) : WorkTagEntryViewModel(workTagsRepository) {

    private val itemId: Int = checkNotNull(savedStateHandle[WorkTagEditDestination.ID_ARG])

    override val otherTagsUiState: StateFlow<WorkTagUiList> =
        workTagsRepository.getOtherWorkTagsStream(tagState.id).map {
            WorkTagUiList(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = WorkTagUiList()
        )

    init {
        viewModelScope.launch {
            tagState = workTagsRepository.getWorkTagStream(itemId)
                .filterNotNull()
                .first()
                .toWorkTagUi()
        }
    }

    /**
     * Deletes the item from the [WorkTagsRepository]'s data source
     * and set child.parentTag to tag.parentTag for all child tags.
     */
    suspend fun deleteTag() {
        val newParent = tagState.parentId
        for (childTag in otherTagsUiState.value.tagList) {
            if (childTag.parentId == itemId) {
                childTag.parentId = newParent
                workTagsRepository.updateWorkTag(childTag)
            }
        }
        workTagsRepository.deleteById(tagState.id)
    }

    override suspend fun saveWorkTag() {
        workTagsRepository.updateWorkTag(tagState.toWorkTag())
    }

}