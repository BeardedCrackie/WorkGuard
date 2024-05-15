package sk.potociarm.workguard.data.workevent

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkEventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workEvent: WorkEvent)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(workEvent: WorkEvent)

    @Delete
    suspend fun delete(workEvent: WorkEvent)

    @Query("DELETE FROM work_event WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * from work_event WHERE id = :id")
    fun getWorkEvent(id: Int): Flow<WorkEvent?>

    @Query("SELECT * from work_event")
    fun getAllWorkEvents(): List<WorkEvent>
    @Query("SELECT * from work_event")
    fun getAllWorkEventsFlow(): Flow<List<WorkEvent>>

    @Transaction
    @Query("SELECT * from work_event WHERE id = :id")
    fun getWorkEventsWithTag(id: Int): Flow<WorkEventWithTag>

    /*
    @Query("SELECT * from work_event GROUP BY start_date")
    fun getWorkEventsMapByDay(): Map<String, List<WorkEvent>>

     */
}
