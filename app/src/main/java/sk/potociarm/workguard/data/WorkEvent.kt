package sk.potociarm.workguard.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "work_event")
data class WorkEvent (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val startTime: String,
    val endTime: String,
    var name: String,
    val description: String
)