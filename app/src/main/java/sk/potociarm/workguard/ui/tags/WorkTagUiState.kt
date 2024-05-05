package sk.potociarm.workguard.ui.tags

import sk.potociarm.workguard.data.worktag.WorkTag

data class WorkTagUiState (
    val workTagDetails: WorkTagDetails = WorkTagDetails(),
    val isEntryValid: Boolean = false
)

data class WorkTagDetails(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val parent: String = "",
)

/**
 * Extension function to convert [WorkTagDetailCard] to [WorkTag]. If the value of [WorkTagDetailCard.price] is
 * not a valid [Double], then the price will be set to 0.0. Similarly if the value of
 * [WorkTagDetailCard.quantity] is not a valid [Int], then the quantity will be set to 0
 */
fun WorkTagDetails.toWorkTag(): WorkTag = WorkTag(
    id = id,
    name = name,
    parentId = parent.toIntOrNull(),
    price = price.toDoubleOrNull() ?: 0.0,
)

/**
 * Extension function to convert [WorkTag] to [WorkTagUiState]
 */
fun WorkTag.toWorkTagUiState(isEntryValid: Boolean = false): WorkTagUiState = WorkTagUiState(
    workTagDetails = this.toWorkTagDetails(),
    isEntryValid = isEntryValid
)

/**
 * Extension function to convert [WorkTag] to [WorkTagDetailCard]
 */
fun WorkTag.toWorkTagDetails(): WorkTagDetails = WorkTagDetails(
    id = id,
    name = name,
    price = price.toString(),
    parent = parentId.toString(),
)
