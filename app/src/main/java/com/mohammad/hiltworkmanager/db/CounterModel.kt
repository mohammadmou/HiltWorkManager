package com.mohammad.hiltworkmanager.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CounterModel(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "counter")
    val counter : Int?
) {
}