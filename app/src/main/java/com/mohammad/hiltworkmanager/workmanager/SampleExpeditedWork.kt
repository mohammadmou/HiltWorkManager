package com.mohammad.hiltworkmanager.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.mohammad.hiltworkmanager.db.AppDatabase
import com.mohammad.hiltworkmanager.db.CounterModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import java.util.*

@HiltWorker
class SampleExpeditedWork @AssistedInject constructor(
    @Assisted applicationContext: Context,
    @Assisted workerParameters: WorkerParameters,
    val appDatabase: AppDatabase
) : CoroutineWorker(applicationContext, workerParameters) {
    override suspend fun doWork(): Result {
        return try {
            val counter = appDatabase
                .counterDao()
                .getCounter(1) ?: CounterModel(1, 1)
            val newCounter = counter.counter?.plus(1)

            //just for test
            delay(5000)

            appDatabase
                .counterDao()
                .insertCounter(CounterModel(1, newCounter))




            Result.success()
        } catch (exception: Exception) {
            Result.failure()
        }
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(1, createNotification(applicationContext))
    }

}