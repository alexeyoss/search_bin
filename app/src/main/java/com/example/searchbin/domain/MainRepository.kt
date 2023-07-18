package com.example.searchbin.domain

import com.example.searchbin.data.network.ResponseStates
import com.example.searchbin.data.db.entities.CachedBinInfoDTO
import com.example.searchbin.data.network.models.BinItem

interface MainRepository {

    suspend fun fetchBinData(binNumber: Long): ResponseStates<List<BinItem>>
    suspend fun getRequestHistory(): ResponseStates<List<CachedBinInfoDTO>>

}