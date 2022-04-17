package com.mohammad.hiltworkmanager.db

import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertCounter(counterModel: CounterModel) : Long

    @Delete
     fun deleteCounter(counterModel: CounterModel)

    @Query("SELECT * FROM countermodel WHERE id = :counterId")
     fun getCounter(counterId: Int): CounterModel?
}