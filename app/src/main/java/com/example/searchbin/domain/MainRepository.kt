package com.example.searchbin.domain

import com.example.searchbin.data.BinItem
import com.example.searchbin.data.network.NetworkCommonBinInfoDTO
import com.example.searchbin.utils.NetworkResponseStates

interface MainRepository {

    suspend fun getBinInfo(binNumber: String): NetworkResponseStates<List<BinItem>>

}