package sk.potociarm.workguard.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "work_tag",
    foreignKeys = [ForeignKey(
        entity = WorkTag::class,
        childColumns = ["parentId"],
        parentColumns = ["id"]
    )])
data class WorkTag (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val parentId: Int?,
    var name: String,
    val price: Double = 0.0
)