package com.example.searchbin.data

import com.example.searchbin.data.network.ApiService
import com.example.searchbin.data.network.NetworkCommonBinInfoDTO.Companion.extractItems
import com.example.searchbin.domain.MainRepository
import com.example.searchbin.utils.NetworkResponseStates
import com.example.searchbin.utils.safeApiCall
import javax.inject.Inject

class MainRepositoryImpl
@Inject constructor(
    private val apiService: ApiService
) : MainRepository {

    override suspend fun getBinInfo(binNumber: String): NetworkResponseStates<List<BinItem>> {
        return safeApiCall {
            apiService.getBinInfo(binNumber).extractItems()
        }
    }

}