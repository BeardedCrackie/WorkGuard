package sk.potociarm.workguard.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import sk.potociarm.workguard.data.workevent.WorkEvent
import sk.potociarm.workguard.data.workevent.WorkEventDao
import sk.potociarm.workguard.data.worktag.WorkTag
import sk.potociarm.workguard.data.worktag.WorkTagDao

@Database(
    entities = [
        WorkEvent::class,
        WorkTag::class
    ],
    version = 4,
    autoMigrations = [
        AutoMigration (from = 1, to = 2),
        AutoMigration (from = 2, to = 3),
        AutoMigration (from = 3, to = 4, spec = WorkGuardDatabase.MyAutoMigration::class)
    ],
    exportSchema = true
)

@TypeConverters(WorkGuardDatabase.Converters::class)
abstract class WorkGuardDatabase : RoomDatabase() {

    @DeleteColumn.Entries(
        DeleteColumn(tableName = "work_event", columnName = "start_time"),
        DeleteColumn(tableName = "work_event", columnName = "start_date"),
        DeleteColumn(tableName = "work_event", columnName = "end_time"),
        DeleteColumn(tableName = "work_event", columnName = "end_date")
    )
    class MyAutoMigration : AutoMigrationSpec



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

    class Converters {

        @TypeConverter
        fun fromLocalDate(value: Int): LocalDate {
            return LocalDate.fromEpochDays(value)
        }

        @TypeConverter
        fun localDateToInt(date: LocalDate): Int {
            return date.toEpochDays()
        }

        @TypeConverter
        fun fromLocalTime(value: Int?): LocalTime? {
            return value?.let { LocalTime.fromSecondOfDay(it) }
        }

        @TypeConverter
        fun localTimeToInt(time: LocalTime?): Int? {
            return time?.toSecondOfDay()
        }
    }
}