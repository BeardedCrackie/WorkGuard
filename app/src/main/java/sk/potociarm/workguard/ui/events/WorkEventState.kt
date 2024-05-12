package sk.potociarm.workguard.ui.events

import sk.potociarm.workguard.data.workevent.Timestamp
import sk.potociarm.workguard.data.workevent.WorkEvent
import sk.potociarm.workguard.data.worktag.WorkTag
import sk.potociarm.workguard.ui.tags.WorkTagState
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

data class WorkEventState(
    val id: Int = 0,
    val tagId: Int? = null,
    val startDateTime: LocalDateTime = LocalDateTime.now(),
    val endDateTime: LocalDateTime? = null,
    var name: String = "name",
    val description: String = "desc",
    val price: Double = 0.0,
    val overridePrice: Boolean = true
) {
    private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    private fun getRunTimeInSeconds() : Long {
        val endTime = endDateTime ?: LocalDateTime.now()
        return startDateTime.until(endTime, ChronoUnit.SECONDS)
    }

    fun getFormattedRunTime() : String {
        var runTime = this.getRunTimeInSeconds()
        val formatter = DecimalFormat("00")
        val hours = formatter.format(runTime/3600) //3600s = 1h
        runTime %= 3600
        val minutes = formatter.format(runTime/60) //60s = 1m
        runTime %= 60
        val seconds = formatter.format(runTime)
        return ("$hours:$minutes:$seconds")
    }

    fun getStartTime() : String {
        return startDateTime.format(timeFormatter)
    }
    fun getStartDate() : String {
        return startDateTime.format(dateFormatter)
    }

    fun getEndTime() : String? {
        return endDateTime?.format(timeFormatter)
    }

    fun getEndDate() : String? {
        return endDateTime?.format(dateFormatter)
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
    startTime = Timestamp(
        date = startDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
        time = startDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    ),
    endTime = if (endDateTime == null) null else Timestamp(
        date = endDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
        time = endDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    ),
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
    startDateTime = LocalDateTime.parse("${startTime.date} ${startTime.time}",
        DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
    endDateTime = if (endTime != null) LocalDateTime.parse("${endTime.date} ${endTime.time}",
        DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) else null,
    description = description,
)

data class WorkEventListState(
    val eventTagsList: List<WorkEvent> = listOf()
)

data class WorkEventStateMap(val eventTagsMap: Map<String, List<WorkEvent>> = mapOf())

fun sampleEventWithTag(): WorkEventState {
    return WorkEventState(
        id = 1,
        tagId = 1,
        startDateTime = LocalDateTime.now().minusHours(16),
        endDateTime = LocalDateTime.now(),
        name = "Test name", description = "Test desc"
    )
}

fun sampleRunningEventWithTag(): WorkEventState {
    return WorkEventState(
        id = 1,
        tagId = 1,
        startDateTime = LocalDateTime.now().minusHours(2).minusMinutes(12),
        endDateTime = null,
        name = "Test name",
        description = "Test desc"
    )
}


fun sampleEventWithoutTag(): WorkEventState {
    return WorkEventState(
        id = 1,
        tagId = null,
        startDateTime = LocalDateTime.now().minusHours(16),
        endDateTime = LocalDateTime.now(),
        name = "Test name",
        description = "Test desc"
    )
}

fun sampleEventMap(): HashMap<String, List<WorkEvent>> {
    val map = HashMap<String, List<WorkEvent>>()
    map["01.01.2022"] = sampleEventList()
    map["02.02.2022"] = sampleEventList()
    return map
}
fun sampleEmptyEventMap(): HashMap<String, List<WorkEvent>> {
    return HashMap()
}


fun sampleEventList(): List<WorkEvent> {
    return listOf(
        WorkEvent(id = 1, price = 10.0, tag = null,
            startTime = Timestamp(date = "01.01.2022", time = "10:10:10"),
            endTime = Timestamp(date = "01.01.2022", time = "12:12:12"),
            name = "first event", description = "first event desc",
            overridePrice = false
        ),
        WorkEvent(id = 2, price = 10.0, tag = null,
            startTime = Timestamp(date = "01.01.2022", time = "10:10:10"),
            endTime = Timestamp(date = "01.01.2022", time = "12:12:12"),
            name = "first event", description = "first event desc",
            overridePrice = false
        ),
        WorkEvent(id = 3, price = 10.0, tag = null,
            startTime = Timestamp(date = "01.01.2022", time = "10:10:10"),
            endTime = Timestamp(date = "01.01.2022", time = "12:12:12"),
            name = "first event", description = "first event desc",
            overridePrice = false
        )
    )
}
