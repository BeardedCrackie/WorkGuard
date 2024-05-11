package sk.potociarm.workguard.data.worktag

import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [WorkTag] from a given data source.
 */
interface WorkTagsRepository {
    /**
     * Retrieve all the WorkTag from the the given data source.
     */
    fun getAllWorkTagsStream(): Flow<List<WorkTag>>

    /**
     * Retrieve all the WorkTags with parent based on id from the the given data source.
     */
    fun getWorkTagChild(id: Int): Flow<List<WorkTag>>

    /**
     * Retrieve an WorkTag from the given data source that matches with the [id].
     */
    fun getWorkTagStream(id: Int): Flow<WorkTag?>

    fun getOtherWorkTagsStream(id: Int): Flow<List<WorkTag>>

    fun getWorkTagParentStream(id: Int): Flow<WorkTag?>

    /**
     * Insert WorkTag in the data source
     */
    suspend fun insertWorkTag(workTag: WorkTag)

    /**
     * Delete WorkTag from the data source
     */
    suspend fun deleteWorkTag(workTag: WorkTag)

    /**
     * Delete WorkTag with selected ID from the data source
     */
    suspend fun deleteById(id: Int)

    /**
     * Update WorkTag in the data source
     */
    suspend fun updateWorkTag(workTag: WorkTag)
}