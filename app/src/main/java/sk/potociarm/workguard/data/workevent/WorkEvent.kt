package sk.potociarm.workguard.data.workevent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import sk.potociarm.workguard.data.worktag.WorkTag

@Entity(tableName = "work_event",
    foreignKeys = [ForeignKey(
    entity = WorkTag::class,
    parentColumns = ["id"],
    childColumns = ["tag"],
)]
)
data class WorkEvent (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val tag: Int?,
    @ColumnInfo(name = "start_time") val startTime: String,
    @ColumnInfo(name = "end_time") val endTime: String,
    var name: String,
    val description: String
)