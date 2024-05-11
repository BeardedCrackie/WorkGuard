package sk.potociarm.workguard.ui.events

import sk.potociarm.workguard.data.workevent.WorkEvent
import sk.potociarm.workguard.data.worktag.WorkTag
import sk.potociarm.workguard.ui.tags.WorkTagState
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

data class WorkEventState(
    val id: Int = 0,
    val tagId: Int?,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime?,
    var name: String,
    val description: String,
) {
    private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    fun getRunTime() : String {
        //val endTime = endDateTime ?: LocalDateTime.now()
        val endTime = LocalDateTime.now()
        val formatter = DecimalFormat("00")
        val hours = formatter.format(startDateTime.until(endTime, ChronoUnit.HOURS))
        val minutes = formatter.format((endTime.minute - startDateTime.minute) % 60)
        val seconds = formatter.format((endTime.second - startDateTime.second) % 60)
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

}

/**
 * Extension function to convert [WorkTagState] to [WorkTag]
 */
fun WorkEventState.toWorkEvent(): WorkEvent = WorkEvent(
    id = id,
    tag = tagId,
    name = name,
    startTime = startDateTime.toString(),
    endTime = endDateTime?.toString(),
    description = description
)

/**
 * Extension function to convert [WorkTag] to [WorkTagState]
 */
fun WorkEvent.toWorkEventState(): WorkEventState = WorkEventState(
    id = id,
    name = name,
    tagId = tag,
    startDateTime = LocalDateTime.parse(startTime),
    endDateTime = if (endTime != null) LocalDateTime.parse(endTime) else null,
    description = description
)

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

