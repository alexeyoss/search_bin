package com.example.searchbin.data

import com.example.searchbin.data.models.NetworkBinInfoDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{bin}")
    suspend fun getNetworkBinInfoDTO(@Path("bin") binNumber: Long): NetworkBinInfoDTO
}