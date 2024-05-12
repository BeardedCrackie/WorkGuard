package sk.potociarm.workguard.ui.tags

import sk.potociarm.workguard.data.worktag.WorkTag
import java.text.NumberFormat

data class WorkTagState(
    val id: Int = 0,
    val name: String = "",
    val price: String = "0.0",
    val parentId: Int? = null,
    var valid: Boolean = false
) {
    fun WorkTagState.formattedPrice() : String {
        return "$price ${NumberFormat.getCurrencyInstance()}"
    }

    fun validate() {
        valid = this.name != "" && this.price.toDoubleOrNull() != null
    }
}

/**
 * Extension function to convert [WorkTagState] to [WorkTag]
 */
fun WorkTagState.toWorkTag(): WorkTag = WorkTag(
    id = id,
    name = name,
    parentId = parentId,
    price = price.toDouble(),
)

/**
 * Extension function to convert [WorkTag] to [WorkTagState]
 */
fun WorkTag.toWorkTagUi(): WorkTagState = WorkTagState(
    id = id,
    name = name,
    price = price.toString(),
    parentId = parentId,
    valid = true
)

fun sampleTagUiWithParent() = WorkTagState(2, "Tag-name", 10.0.toString(), 1)

fun sampleTagUiWithoutParent() = WorkTagState(2, "Tag-name", 10.0.toString(), null)

