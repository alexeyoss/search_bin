package com.example.searchbin.data

import com.example.searchbin.data.db.entities.CachedBinInfoDTO
import com.example.searchbin.data.models.NetworkBinInfoDTO

interface CachedBinInfoStore {

    suspend fun saveNetworkBinInfoDTOWithTimeStamp(
        networkBinInfoDTO: NetworkBinInfoDTO,
        binNumber: Long
    )

    suspend fun isCached(binNumber: Long): Boolean
    suspend fun getBinData(binNumber: Long): CachedBinInfoDTO

    suspend fun isHistoryDataNotEmpty(): Boolean
    suspend fun getHistoryData(): List<CachedBinInfoDTO>
}
