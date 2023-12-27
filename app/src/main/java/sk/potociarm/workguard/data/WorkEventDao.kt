package sk.potociarm.workguard.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkEventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workEvent: WorkEvent)

    @Update
    suspend fun update(workEvent: WorkEvent)

    @Delete
    suspend fun delete(workEvent: WorkEvent)

    @Query("SELECT * from work_event WHERE id = :id")
    fun getWorkEvent(id: Int): Flow<WorkEvent>

    @Query("SELECT * from work_event")
    fun getAllWorkEvents(): Flow<List<WorkEvent>>
}
