package sk.potociarm.workguard.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "work_tag",
    foreignKeys =
    [ForeignKey(
        entity = WorkTag::class,
        parentColumns = ["id"],
        childColumns = ["parent_id"]
    )]
    )
data class WorkTag (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "parent_id") val parentId: Int?,
    var name: String,
    val price: Double = 0.0
)