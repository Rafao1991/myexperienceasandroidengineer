package com.rafao1991.myexperienceasandroidengineer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rafao1991.myexperienceasandroidengineer.data.dao.ExperienceDao
import com.rafao1991.myexperienceasandroidengineer.data.entity.DatabaseExperience

@Database(entities = [DatabaseExperience::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun experienceDao(): ExperienceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext, AppDatabase::class.java, DATABASE_NAME
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}