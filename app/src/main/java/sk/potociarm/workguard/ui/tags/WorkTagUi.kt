package sk.potociarm.workguard.ui.tags

import sk.potociarm.workguard.data.worktag.WorkTag
import java.text.NumberFormat

data class WorkTagUi(
    val id: Int = 0,
    var name: String = "",
    var price: String = "0.0",
    var parentId: Int? = null,
) {
    fun WorkTagUi.formattedPrice() : String {
        return "$price ${NumberFormat.getCurrencyInstance()}"
    }
}

/**
 * Extension function to convert [WorkTagUi] to [WorkTag]
 */
fun WorkTagUi.toWorkTag(): WorkTag = WorkTag(
    id = id,
    name = name,
    parentId = parentId,
    price = price.toDouble(),
)

/**
 * Extension function to convert [WorkTag] to [WorkTagUi]
 */
fun WorkTag.toWorkTagUi(): WorkTagUi = WorkTagUi(
    id = id,
    name = name,
    price = price.toString(),
    parentId = parentId,
)

fun sampleTagUiWithParent() = WorkTagUi(2, "Tag-name", 10.0.toString(), 1)

fun sampleTagUiWithoutParent() = WorkTagUi(2, "Tag-name", 10.0.toString(), null)

