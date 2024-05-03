package sk.potociarm.workguard.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "work_event",
    foreignKeys = [androidx.room.ForeignKey(
    entity = WorkTag::class,
    childColumns = ["tagId"],
    parentColumns = ["id"]
)])
data class WorkEvent (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val tagId: Int,
    val startTime: String,
    val endTime: String,
    var name: String,
    val description: String
)