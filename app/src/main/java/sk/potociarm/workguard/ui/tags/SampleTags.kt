package sk.potociarm.workguard.ui.tags

import androidx.compose.runtime.Composable
import sk.potociarm.workguard.data.worktag.WorkTag

@Composable
public fun sampleTagList() = listOf(
    WorkTag(1, 1, "Tag 1", 10.0),
    WorkTag(2, 1, "Tag 2", 20.0),
    WorkTag(3, null, "Tag 3", 200.0),
)

public fun sampleTagWithParent() = WorkTag(2, 1, "Tag-name", 10.0)

public fun sampleTagWithoutParent() = WorkTag(2, null, "Tag-name", 10.0)
