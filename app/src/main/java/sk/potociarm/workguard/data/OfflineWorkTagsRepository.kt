package sk.potociarm.workguard.data

import kotlinx.coroutines.flow.Flow

class OfflineWorkTagsRepository(private val workTagDao: WorkTagDao) : WorkTagsRepository {
    override fun getAllWorkTagsStream(): Flow<List<WorkTag>> = workTagDao.getAllWorkTags()

    override fun getWorkTagParentStream(id: Int): Flow<WorkTag?> = workTagDao.getWorkTagParent(id)

    override fun getWorkTagStream(id: Int): Flow<WorkTag?> = workTagDao.getWorkTag(id)

    override suspend fun insertWorkTag(workTag: WorkTag) = workTagDao.insert(workTag)

    override suspend fun deleteWorkTag(workTag: WorkTag) = workTagDao.delete(workTag)

    override suspend fun updateWorkTag(workTag: WorkTag) = workTagDao.update(workTag)
}