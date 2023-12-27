package sk.potociarm.workguard.data

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room

@Database(entities = [WorkEvent::class], version = 1, exportSchema = false)
abstract class WorkEventDatabase : RoomDatabase() {
    abstract fun workEventDao(): WorkEventDao

    companion object {

        @Volatile
        private var Instance: WorkEventDatabase? = null

        fun getDatabase(context: Context): WorkEventDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, WorkEventDatabase::class.java, "work_event_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}