package com.example.searchbin.data.db.converters

import androidx.room.TypeConverter
import com.example.searchbin.data.models.NetworkBinInfoDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NetworkBinInfoDTOConverter {

    private val gson by lazy { Gson() }

    @TypeConverter
    fun responseToString(networkCommonBinInfoDTO: NetworkBinInfoDTO): String {
        return gson.toJson(networkCommonBinInfoDTO)
    }

    @TypeConverter
    fun stringToResponse(data: String): NetworkBinInfoDTO {
        val listType = object : TypeToken<NetworkBinInfoDTO>() {}.type
        return gson.fromJson(data, listType)
    }
}