package sk.potociarm.workguard.ui.tags

import sk.potociarm.workguard.data.worktag.WorkTag

/**
 * Ui State for HomeScreen
 */
data class WorkTagUiList(val tagList: List<WorkTag> = listOf())

public fun sampleTagUiList() = listOf(
    WorkTag(1, 1, "Tag 1", 10.0),
    WorkTag(2, 1, "Tag 2", 20.0),
    WorkTag(3, null, "Tag 3", 200.0),
)
