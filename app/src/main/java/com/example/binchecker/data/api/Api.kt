package com.example.binchecker.data.api

import com.example.binchecker.model.BinModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("{bin}")
    suspend fun getBinDescription(@Path("bin") bin : String?) : Response<BinModel>
}