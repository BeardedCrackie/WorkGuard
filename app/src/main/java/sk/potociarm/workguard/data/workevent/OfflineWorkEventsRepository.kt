package sk.potociarm.workguard.data.workevent

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

class OfflineWorkEventsRepository(private val workEventDao: WorkEventDao) : WorkEventsRepository {
    override fun getAllWorkEvents(): List<WorkEvent> = workEventDao.getAllWorkEvents()

    override fun getAllWorkEventsStream(): Flow<List<WorkEvent>> = workEventDao.getAllWorkEventsFlow()

    override fun getWorkEventStream(id: Int): Flow<WorkEvent?> = workEventDao.getWorkEvent(id)

    override suspend fun insertWorkEvent(workEvent: WorkEvent) = workEventDao.insert(workEvent)

    override suspend fun deleteWorkEvent(workEvent: WorkEvent) = workEventDao.delete(workEvent)

    override suspend fun updateWorkEvent(workEvent: WorkEvent) = workEventDao.update(workEvent)

    override fun getWorkEventsGroupedByDay(): Flow<Map<LocalDate, List<WorkEvent>>> {
        return workEventDao.getAllWorkEventsFlow()
            .map { it.groupBy { LocalDate.fromEpochDays(it.date) } }
    }


}