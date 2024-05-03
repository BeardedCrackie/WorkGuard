package sk.potociarm.workguard.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
    val startTime: String,
    val endTime: String,
    var name: String,
    val description: String
)