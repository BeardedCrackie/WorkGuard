package sk.potociarm.workguard.data.worktag

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkTagDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workTag: WorkTag)

    @Update
    suspend fun update(workTag: WorkTag)

    @Delete
    suspend fun delete(workTag: WorkTag)

    @Query("SELECT * FROM work_tag WHERE id = :id")
    fun getWorkTag(id: Int): Flow<WorkTag?>

    @Query("SELECT p.* FROM work_tag p JOIN work_tag c on p.id = c.parent_id WHERE c.id = :id")
    fun getWorkTagParent(id: Int): Flow<WorkTag>

    @Query("SELECT * FROM work_tag WHERE parent_id = :id")
    fun getWorkTagChild(id: Int): Flow<List<WorkTag>>

    @Query("SELECT * FROM work_tag")
    fun getAllWorkTags(): Flow<List<WorkTag>>

    @Query("SELECT * FROM work_tag WHERE id != :id")
    fun getOtherWorkTags(id: Int): Flow<List<WorkTag>>
}