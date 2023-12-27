package sk.potociarm.workguard.data

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

    @Query("SELECT * from work_tag WHERE id = :id")
    fun getWorkTag(id: Int): Flow<WorkTag>

    @Query("SELECT * from work_tag")
    fun getAllWorkTags(): Flow<List<WorkTag>>
}