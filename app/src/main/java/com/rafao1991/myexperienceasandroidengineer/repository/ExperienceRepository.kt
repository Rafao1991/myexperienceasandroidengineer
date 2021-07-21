package com.rafao1991.myexperienceasandroidengineer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.rafao1991.myexperienceasandroidengineer.data.AppDatabase
import com.rafao1991.myexperienceasandroidengineer.data.entity.DatabaseExperience
import com.rafao1991.myexperienceasandroidengineer.data.entity.asDomainModel
import com.rafao1991.myexperienceasandroidengineer.model.Experience
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExperienceRepository(private val database: AppDatabase) {

    val experiences: LiveData<List<Experience>> =
        Transformations.map(database.experienceDao().getAll()) {
            it.asDomainModel()
        }

    suspend fun loadData(experiences: List<DatabaseExperience>) {
        withContext(Dispatchers.IO) {
            database.experienceDao().insertAll(experiences)
        }
    }
}