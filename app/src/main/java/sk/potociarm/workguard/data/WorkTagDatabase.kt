package sk.potociarm.workguard.data

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room

@Database(entities = [WorkTag::class], version = 1, exportSchema = false)
abstract class WorkTagDatabase : RoomDatabase() {
    abstract fun workTagDao(): WorkTagDao

    companion object {
        @Volatile
        private var Instance: WorkTagDatabase? = null
        fun getDatabase(context: Context): WorkTagDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, WorkTagDatabase::class.java, "work_tag_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }

}