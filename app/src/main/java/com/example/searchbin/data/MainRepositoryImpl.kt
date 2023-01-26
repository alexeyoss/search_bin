package com.example.searchbin.data

import com.example.searchbin.data.entities.CachedBinInfoDTO
import com.example.searchbin.data.mappers.CachedBinInfoMapper
import com.example.searchbin.data.models.BinItem
import com.example.searchbin.data.models.NetworkBinInfoDTO.Companion.extractListOfBinItems
import com.example.searchbin.domain.MainRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class MainRepositoryImpl
@Inject constructor(
    private val apiService: ApiService,
    private val cachedBinInfoStore: CachedBinInfoStore,
    private val cachedBinInfoMapper: CachedBinInfoMapper,
) : MainRepository {
    override suspend fun fetchBinData(binNumber: Long): ResponseStates<List<BinItem>> {
        return when (val cacheResult = fetchBinInfoFromDb(binNumber)) {
            is ResponseStates.Success -> {
                val binItems =
                    cachedBinInfoMapper.mapToForeignModel(cacheResult.data).extractListOfBinItems()
                ResponseStates.Success(binItems)
            }
            else -> fetchAndCacheBinDataFromNetwork(binNumber)
        }
    }

    private suspend fun fetchBinInfoFromDb(binNumber: Long): ResponseStates<CachedBinInfoDTO> {
        return safeCall {
            cachedBinInfoStore.getBinInfo(binNumber).first() // TODO refactor
        }
    }


    private suspend fun fetchAndCacheBinDataFromNetwork(binNumber: Long): ResponseStates<List<BinItem>> {
        return safeCall {
            val networkResult = apiService.getNetworkBinInfoDTO(binNumber)

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
        return safeCall {
            cachedBinInfoStore.getRequestHistory()
        }
    }
}