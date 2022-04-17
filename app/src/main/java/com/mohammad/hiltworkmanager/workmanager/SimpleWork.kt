package com.mohammad.hiltworkmanager.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mohammad.hiltworkmanager.db.AppDatabase
import com.mohammad.hiltworkmanager.db.CounterModel

class SimpleWork(applicationContext: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(applicationContext, workerParameters) {
    override suspend fun doWork(): Result {
        return try {
            showNotification(applicationContext)
            val counter = AppDatabase.getDatabase(applicationContext)
                .counterDao()
                .getCounter(1) ?: CounterModel(1, 1)
            val newCounter = counter.counter?.plus(1)


            AppDatabase.getDatabase(applicationContext)
                .counterDao()
                .insertCounter(CounterModel(1, newCounter))


            Result.success()
        } catch (exception: Exception) {
            Result.failure()
        }


    }

}