package com.example.ny.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ny.model.SATScores
import com.example.ny.model.School
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [School::class, SATScores::class], version = 1, exportSchema = false)
abstract class SchoolRoomDatabase : RoomDatabase() {

    abstract fun schoolDao(): SchoolDao
    abstract fun satScoresDao(): SATScoresDao

    companion object {
        @Volatile
        private var INSTANCE: SchoolRoomDatabase? = null
        private const val NUMBER_OF_THREADS = 10

        // Uses the Executer Service to run DB operations in background and concurrently
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(
            NUMBER_OF_THREADS
        )

        /**
         * DB Singleton
         * @param context
         * @return
         */
        fun getDatabase(context: Context): SchoolRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(SchoolRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            SchoolRoomDatabase::class.java, "school_database"
                        )
                            .addCallback(sRoomDatabaseCallback)
                            .build()
                    }
                }
            }
            return INSTANCE
        }

        /**
         * Handle any setup after DB is created for the first time
         */
        private val sRoomDatabaseCallback: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                databaseWriteExecutor.execute {
                    val schoolDao = INSTANCE!!.schoolDao()
                    schoolDao.deleteAll()
                    val scoresDao =
                        INSTANCE!!.satScoresDao()
                    scoresDao.deleteAll()
                }
            }
        }
    }
}
