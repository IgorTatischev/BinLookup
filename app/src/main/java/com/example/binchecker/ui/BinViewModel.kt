package com.example.binchecker.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.binchecker.data.Repository
import com.example.binchecker.model.BinModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BinViewModel(application: Application): AndroidViewModel(application) {

    private val repository: Repository

    private val _description : MutableLiveData<BinModel> = MutableLiveData()
    val description : LiveData<BinModel> = _description

    init {
        repository = Repository(application)
    }

    fun getList(): LiveData<List<BinModel>>{
        return repository.listBins
    }

    fun  getDescription(bin: String){
        viewModelScope.launch {
            val value = repository.getBinDescription(bin).body()
            val binModel = value?.let {
                BinModel(bin = bin.toInt(), bank = it.bank ,
                    brand = it.brand, country = it.country,
                    number = it.number, prepaid = it.prepaid,
                    scheme = it.scheme, type = it.type)
            }
            if (binModel != null) {
                repository.insertDescription(binModel)
            }
            _description.value = value!!
        }
    }

    fun deleteDescription(bin: BinModel){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteDescription(bin)
        }
    }
}
