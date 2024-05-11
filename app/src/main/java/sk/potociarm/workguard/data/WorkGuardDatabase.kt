package sk.potociarm.workguard.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import sk.potociarm.workguard.data.workevent.WorkEvent
import sk.potociarm.workguard.data.workevent.WorkEventDao
import sk.potociarm.workguard.data.worktag.WorkTag
import sk.potociarm.workguard.data.worktag.WorkTagDao

@Database(
    entities = [
        WorkEvent::class,
        WorkTag::class
    ],
    version = 2,
    autoMigrations = [
        AutoMigration (from = 1, to = 2)
    ],
    exportSchema = true
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