package com.example.searchbin.data

import com.example.searchbin.data.db.BinDb
import com.example.searchbin.data.entities.CachedBinInfoDTO
import com.example.searchbin.data.mappers.CachedBinInfoMapper
import com.example.searchbin.data.models.BinItem
import com.example.searchbin.data.models.NetworkBinInfoDTO
import com.example.searchbin.di.CoroutinesModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CachedBinInfoStoreImpl
@Inject
constructor(
    binDb: BinDb,
    @CoroutinesModule.IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val cachedBinInfoMapper: CachedBinInfoMapper
) : CachedBinInfoStore {

    private val cachedBinInfoDao = binDb.binDao()

    override suspend fun saveNetworkBinInfoDTOWithTimeStamp(
        networkBinInfoDTO: NetworkBinInfoDTO,
        binNumber: Long
    ) = withContext(ioDispatcher) {
        val cachedBinInfo = cachedBinInfoMapper.mapToDomainModel(networkBinInfoDTO, binNumber)
        cachedBinInfoDao.insertWithTimeStamp(cachedBinInfo)
    }

    override suspend fun getBinInfo(binNumber: Long): List<CachedBinInfoDTO> {
        return withContext(ioDispatcher) {
            cachedBinInfoDao.getBinData(binNumber)
        }
    }

    override suspend fun getRequestHistory(): List<CachedBinInfoDTO> {
        return withContext(ioDispatcher) {
            cachedBinInfoDao.getAllBinData()
        }
    }
}