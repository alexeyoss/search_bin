package com.example.searchbin.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{bin}")
    suspend fun getBinInfo(@Path("bin") binNumber: String): NetworkCommonBinInfoDTO
}