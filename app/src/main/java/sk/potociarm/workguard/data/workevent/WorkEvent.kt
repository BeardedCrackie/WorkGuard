package sk.potociarm.workguard.data.workevent

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
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
    @ColumnInfo(defaultValue = "0") val date: Int,
    @ColumnInfo(defaultValue = "0") val startTime: Int,
    val endTime: Int?,
    var name: String,
    val description: String,
    @ColumnInfo(defaultValue = "0.0") val price: Double,
    @ColumnInfo(defaultValue = false.toString()) val overridePrice: Boolean
)

data class WorkEventWithTag (
    @Embedded val event: WorkEvent,
    @Relation(
        parentColumn = "tag",
        entityColumn = "id")
    val tag: WorkTag?,
)