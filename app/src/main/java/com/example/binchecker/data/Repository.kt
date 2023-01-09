package com.example.binchecker.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.binchecker.data.api.Retrofit
import com.example.binchecker.data.room.BinDao
import com.example.binchecker.data.room.BinDatabase
import com.example.binchecker.model.BinModel
import retrofit2.Response


class Repository(application: Application) {

    private val binDao: BinDao

    init {
        binDao = BinDatabase.getInstance(application).getBinDao()
    }

    val listBins: LiveData<List<BinModel>>
        get() = binDao.getListBins()

    suspend fun getBinDescription(bin: String): Response<BinModel>{
        return Retrofit.instance.getBinDescription(bin)
    }

    suspend fun insertDescription(binModel: BinModel) {
        binDao.insert(binModel)
    }

    suspend fun deleteDescription(binModel: BinModel) {
        binDao.delete(binModel)
    }

}