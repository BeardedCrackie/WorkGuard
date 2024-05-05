package sk.potociarm.workguard.data.workevent

import kotlinx.coroutines.flow.Flow

class OfflineWorkEventsRepository(private val workEventDao: WorkEventDao) : WorkEventsRepository {
    override fun getAllWorkEventsStream(): Flow<List<WorkEvent>> = workEventDao.getAllWorkEvents()

    override fun getWorkEventStream(id: Int): Flow<WorkEvent?> = workEventDao.getWorkEvent(id)

    override suspend fun insertWorkEvent(workEvent: WorkEvent) = workEventDao.insert(workEvent)

    override suspend fun deleteWorkEvent(workEvent: WorkEvent) = workEventDao.delete(workEvent)

    override suspend fun updateWorkEvent(workEvent: WorkEvent) = workEventDao.update(workEvent)
}