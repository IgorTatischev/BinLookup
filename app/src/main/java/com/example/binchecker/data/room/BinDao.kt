package com.example.binchecker.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.binchecker.model.BinModel

@Dao
interface BinDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(binModel: BinModel)

    @Delete
    suspend fun delete(binModel: BinModel)

    @Query("SELECT * FROM description_table")
    fun getListBins(): LiveData<List<BinModel>>
}