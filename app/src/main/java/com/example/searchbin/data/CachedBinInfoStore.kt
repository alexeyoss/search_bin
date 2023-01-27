package com.example.searchbin.data

import com.example.searchbin.data.entities.CachedBinInfoDTO
import com.example.searchbin.data.models.NetworkBinInfoDTO

interface CachedBinInfoStore {

    suspend fun saveNetworkBinInfoDTOWithTimeStamp(
        networkBinInfoDTO: NetworkBinInfoDTO,
        binNumber: Long
    )

    suspend fun getBinInfo(binNumber: Long): List<CachedBinInfoDTO>

    suspend fun getRequestHistory(): List<CachedBinInfoDTO>
}
