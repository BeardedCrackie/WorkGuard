package sk.potociarm.workguard.ui.events


import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import sk.potociarm.workguard.data.workevent.WorkEvent
import sk.potociarm.workguard.data.worktag.WorkTag
import sk.potociarm.workguard.ui.tags.WorkTagState
import java.time.LocalDateTime
import java.time.Month

data class WorkEventState(
    val id: Int = 0,
    val tagId: Int? = null,
    val date: LocalDate = LocalDate(2024,1,1),
    val startTime: LocalTime = LocalTime(0,0),
    val endTime: LocalTime? = null,
    var name: String = "name",
    val description: String = "desc",
    val price: Double = 0.0,
    val overridePrice: Boolean = true
) {
    private fun getRunTimeInSeconds() : Int {
        val end = endTime?.toSecondOfDay() ?:
        LocalDateTime.now().toLocalTime().toSecondOfDay()
        val start = startTime.toSecondOfDay()
        if (end > start) {
            return end - start;
        }
        return 86400 - start + end
    }

    fun getRunTime() : LocalTime {
        return LocalTime.fromSecondOfDay(getRunTimeInSeconds())
    }

    fun computeEarn() : Double {
        return this.price * (getRunTimeInSeconds().toDouble()/3600)
    }

}

/**
 * Extension function to convert [WorkTagState] to [WorkTag]
 */
fun WorkEventState.toWorkEvent(): WorkEvent = WorkEvent(
    id = id,
    tag = tagId,
    name = name,
    date = date.toEpochDays(),
    startTime = startTime.toSecondOfDay(),
    endTime = endTime?.toSecondOfDay(),
    description = description,
    price = price,
    overridePrice = overridePrice
)

/**
 * Extension function to convert [WorkTag] to [WorkTagState]
 */
fun WorkEvent.toWorkEventState(): WorkEventState = WorkEventState(
    id = id,
    name = name,
    tagId = tag,
    date = LocalDate.fromEpochDays(date),
    startTime = LocalTime.fromSecondOfDay(startTime),
    endTime = endTime?.let { LocalTime.fromSecondOfDay(it) },
    description = description,
    price = price
)

data class WorkEventListState(
    val eventTagsList: List<WorkEvent> = listOf()
)

data class WorkEventStateMap(val eventTagsMap: Map<String, List<WorkEvent>> = mapOf())

fun sampleEventWithTag(): WorkEventState {
    return sampleRunningEventWithTag().copy(
        endTime = LocalTime(hour = 10, minute = 10)
    )
}

fun sampleRunningEventWithTag(): WorkEventState {
    return WorkEventState(
        id = 1,
        date = LocalDate(year = 2024, month = Month.JANUARY, dayOfMonth = 1),
        startTime = LocalTime(hour = 10, minute = 10),
        name = "Test name", description = "Test desc",
        price = 10.5
    )
}


fun sampleEventWithoutTag(): WorkEventState {
    return sampleRunningEventWithTag().copy(
        tagId = 1)
}

fun sampleEventMap(): HashMap<LocalDate, List<WorkEvent>> {
    val map = HashMap<LocalDate, List<WorkEvent>>()
    map[LocalDate(year = 2024, month = Month.JANUARY, dayOfMonth = 1)] = sampleEventList()
    map[LocalDate(year = 2024, month = Month.JANUARY, dayOfMonth = 2)] = sampleEventList()
    return map
}
fun sampleEmptyEventMap(): HashMap<LocalDate, List<WorkEvent>> {
    return HashMap()
}

fun sampleEventList(): List<WorkEvent> {
    return listOf(
        sampleEventWithTag().toWorkEvent(),
        sampleEventWithTag().toWorkEvent(),
        sampleEventWithTag().toWorkEvent()
        )
}
