package com.example.searchbin.domain

import com.example.searchbin.data.models.BinItem
import com.example.searchbin.data.ResponseStates
import com.example.searchbin.data.entities.CachedBinInfoDTO

interface MainRepository {

    suspend fun fetchBinData(binNumber: Long): ResponseStates<List<BinItem>>
    suspend fun getRequestHistory(): ResponseStates<List<CachedBinInfoDTO>>

}