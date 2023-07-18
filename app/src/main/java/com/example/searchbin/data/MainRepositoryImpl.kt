package com.example.searchbin.data

import com.example.searchbin.data.db.entities.CachedBinInfoDTO
import com.example.searchbin.data.network.models.BinItem
import com.example.searchbin.data.network.models.NetworkBinInfoDTO.Companion.extractListOfBinItems
import com.example.searchbin.data.network.ApiService
import com.example.searchbin.data.network.ErrorState
import com.example.searchbin.data.network.ResponseStates
import com.example.searchbin.data.network.safeCall
import com.example.searchbin.domain.MainRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class MainRepositoryImpl
@Inject constructor(
    private val apiService: ApiService, private val cachedBinInfoStore: CachedBinInfoStore
) : MainRepository {
    override suspend fun fetchBinData(binNumber: Long): ResponseStates<List<BinItem>> {
        return if (cachedBinInfoStore.isCached(binNumber)) {
            val binItemsList =
                cachedBinInfoStore.getBinData(binNumber).response.extractListOfBinItems()
            ResponseStates.Success(binItemsList)
        } else {
            fetchAndCacheBinDataFromNetwork(binNumber)
        }
    }


    private suspend fun fetchAndCacheBinDataFromNetwork(binNumber: Long): ResponseStates<List<BinItem>> {
        return safeCall {
            val networkResult = apiService.getNetworkBinInfoDTO(binNumber)

            /**guarantee that operation will done if the coroutine scope will fall*/

            /**guarantee that operation will done if the coroutine scope will fall*/
            coroutineScope {
                cachedBinInfoStore.saveNetworkBinInfoDTOWithTimeStamp(
                    networkResult, binNumber
                )
            }
            networkResult.extractListOfBinItems()
        }
    }


    override suspend fun getRequestHistory(): ResponseStates<List<CachedBinInfoDTO>> {
        return if (cachedBinInfoStore.isHistoryDataNotEmpty()) {
            safeCall { cachedBinInfoStore.getHistoryData() }
        } else {
            ErrorState.SuccessNoResult
        }

    }
}