package com.example.searchbin.data.network

import com.example.searchbin.data.network.models.NetworkBinInfoDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{bin}")
    suspend fun getNetworkBinInfoDTO(@Path("bin") binNumber: Long): NetworkBinInfoDTO
}