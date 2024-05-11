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
    startTime = startDateTime.toString(),
    endTime = endDateTime?.toString(),
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
    startDateTime = LocalDateTime.parse(startTime),
    endDateTime = if (endTime != null) LocalDateTime.parse(endTime) else null,
    description = description,
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
