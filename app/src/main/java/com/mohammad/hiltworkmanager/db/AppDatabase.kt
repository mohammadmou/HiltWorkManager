package com.mohammad.hiltworkmanager.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CounterModel::class] , version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun counterDao() : Dao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context, AppDatabase::class.java, "counter_database")
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}