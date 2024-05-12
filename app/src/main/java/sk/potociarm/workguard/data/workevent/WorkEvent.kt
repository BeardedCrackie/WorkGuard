package sk.potociarm.workguard.data.workevent

import androidx.room.ColumnInfo
import androidx.room.Embedded
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
    @Embedded(prefix = "start_") val startTime: Timestamp,
    @Embedded(prefix = "end_") val endTime: Timestamp?,
    var name: String,
    val description: String,
    @ColumnInfo(defaultValue = "0.0") val price: Double,
    @ColumnInfo(defaultValue = false.toString()) val overridePrice: Boolean
)

data class Timestamp (
    @ColumnInfo(defaultValue = "") val date: String,
    @ColumnInfo(defaultValue = "") val time: String
)