package sk.potociarm.workguard.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "work_tag")
data class WorkTag (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    val price: Double = 0.0
)