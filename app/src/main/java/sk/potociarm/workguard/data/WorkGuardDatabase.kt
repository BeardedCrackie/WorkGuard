package sk.potociarm.workguard.data

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room
import sk.potociarm.workguard.data.workevent.WorkEvent
import sk.potociarm.workguard.data.workevent.WorkEventDao
import sk.potociarm.workguard.data.worktag.WorkTag
import sk.potociarm.workguard.data.worktag.WorkTagDao

@Database(
    entities = [
        WorkEvent::class,
        WorkTag::class
    ],
    version = 1, exportSchema = false
)
abstract class WorkGuardDatabase : RoomDatabase() {
    abstract fun workEventDao(): WorkEventDao
    abstract fun workTagDao(): WorkTagDao

    companion object {
        @Volatile
        private var Instance: WorkGuardDatabase? = null

        fun getDatabase(context: Context): WorkGuardDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, WorkGuardDatabase::class.java, "work_guard_database")
                    .build().also {Instance = it}
            }
        }
    }
}