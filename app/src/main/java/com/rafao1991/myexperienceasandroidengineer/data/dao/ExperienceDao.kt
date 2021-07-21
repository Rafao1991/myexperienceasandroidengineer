package com.rafao1991.myexperienceasandroidengineer.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rafao1991.myexperienceasandroidengineer.data.entity.DatabaseExperience

@Dao
interface ExperienceDao {
    @Query("SELECT * FROM experiences")
    fun getAll(): LiveData<List<DatabaseExperience>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(experiences: List<DatabaseExperience>)
}