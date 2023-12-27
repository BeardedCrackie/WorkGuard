package sk.potociarm.workguard.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [WorkEvent] from a given data source.
 */
interface WorkEventsRepository {
    /**
     * Retrieve all the WorkEvent from the the given data source.
     */
    fun getAllWorkEventsStream(): Flow<List<WorkEvent>>

    /**
     * Retrieve an WorkEvent from the given data source that matches with the [id].
     */
    fun getWorkEventStream(id: Int): Flow<WorkEvent?>

    /**
     * Insert WorkEvent in the data source
     */
    suspend fun insertWorkEvent(workEvent: WorkEvent)

    /**
     * Delete WorkEvent from the data source
     */
    suspend fun deleteWorkEvent(workEvent: WorkEvent)

    /**
     * Update WorkEvent in the data source
     */
    suspend fun updateWorkEvent(workEvent: WorkEvent)
}