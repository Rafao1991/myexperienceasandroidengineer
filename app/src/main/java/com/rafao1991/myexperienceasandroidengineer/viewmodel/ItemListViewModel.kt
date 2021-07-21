package com.rafao1991.myexperienceasandroidengineer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.rafao1991.myexperienceasandroidengineer.data.AppDatabase
import com.rafao1991.myexperienceasandroidengineer.data.EXP_DATA_FILENAME
import com.rafao1991.myexperienceasandroidengineer.model.Experience
import com.rafao1991.myexperienceasandroidengineer.repository.ExperienceRepository
import com.rafao1991.myexperienceasandroidengineer.worker.DatabaseWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ItemListViewModel(application: Application) : AndroidViewModel(application) {
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val database = AppDatabase.getInstance(application)
    private val experienceRepository = ExperienceRepository(database)

    val viewExperience : LiveData<List<Experience>> = experienceRepository.experiences

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun loadData() {
        viewModelScope.launch {
            val request = OneTimeWorkRequestBuilder<DatabaseWorker>()
                .setInputData(workDataOf(DatabaseWorker.KEY_FILENAME to EXP_DATA_FILENAME))
                .build()

            WorkManager.getInstance(getApplication())
                .enqueueUniqueWork(
                    DatabaseWorker.TAG,
                    ExistingWorkPolicy.APPEND_OR_REPLACE,
                    request)
        }
    }
}