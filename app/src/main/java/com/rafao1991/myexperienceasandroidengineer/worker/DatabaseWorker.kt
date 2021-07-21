package com.rafao1991.myexperienceasandroidengineer.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.rafao1991.myexperienceasandroidengineer.data.AppDatabase
import com.rafao1991.myexperienceasandroidengineer.data.entity.DatabaseExperience
import com.rafao1991.myexperienceasandroidengineer.repository.ExperienceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val database = AppDatabase.getInstance(applicationContext)
            val repository = ExperienceRepository(database)

            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null) {
                applicationContext.assets.open(filename).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val type = object : TypeToken<List<DatabaseExperience>>() {}.type
                        val experiences: List<DatabaseExperience> = Gson().fromJson(jsonReader, type)

                        try {
                            repository.loadData(experiences)
                            Result.success()
                        } catch (e: Exception) {
                            Result.retry()
                        }
                    }
                }
            } else {
                Log.e(TAG, "Error with database - no valid filename")
                Result.failure()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error with database", ex)
            Result.failure()
        }
    }

    companion object {
        const val TAG = "DatabaseWorker"
        const val KEY_FILENAME = "EXP_DATA_FILENAME"
    }
}