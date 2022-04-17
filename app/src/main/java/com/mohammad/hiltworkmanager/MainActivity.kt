package com.mohammad.hiltworkmanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.mohammad.hiltworkmanager.databinding.ActivityMainBinding
import com.mohammad.hiltworkmanager.db.AppDatabase
import com.mohammad.hiltworkmanager.db.CounterModel
import com.mohammad.hiltworkmanager.workmanager.SampleExpeditedWork
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var countNumber = 0

    @Inject
    lateinit var appDatabase: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WorkManager.getInstance(applicationContext)
            .getWorkInfosByTagLiveData("myExpeditedTask").observe(this) { workinfoes ->
                if (workinfoes != null && workinfoes.size != 0) {
                    if (workinfoes[0].state.isFinished) {
                        flow {
                            val counter = AppDatabase.getDatabase(applicationContext)
                                .counterDao()
                                .getCounter(1)
                            emit(counter)
                        }.flowOn(Dispatchers.IO)
                            .onEach {
                                binding.text1.text = it.toString()
                            }
                            .launchIn(MainScope())

                    }
                }
            }



        binding.workerBtn.setOnClickListener {
            //sample constrains just for test
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = OneTimeWorkRequestBuilder<SampleExpeditedWork>()
                .addTag("myExpeditedTask")
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(applicationContext)
                .beginUniqueWork("myuniquework", ExistingWorkPolicy.REPLACE, workRequest)
                .enqueue()
        }



        binding.read.setOnClickListener {
            flow {
                val counter = appDatabase
                    .counterDao()
                    .getCounter(1)
                emit(counter)
            }.flowOn(Dispatchers.IO)
                .onEach {
                    Toast.makeText(this, it?.toString() ?: "nothing", Toast.LENGTH_SHORT).show()
                }.launchIn(MainScope())


        }

        binding.insertDB.setOnClickListener {
            countNumber
            flow<Int> {
                appDatabase
                    .counterDao()
                    .insertCounter(CounterModel(id = 1, counter = countNumber))
                emit(0)
            }.flowOn(Dispatchers.IO).onEach {
                binding.text1.text = CounterModel(1,it).toString()
                Toast.makeText(this, "Start From 0", Toast.LENGTH_SHORT).show()
            }.launchIn(MainScope())
        }


    }


}