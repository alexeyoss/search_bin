package com.example.searchbin.data.db.converters

import androidx.room.TypeConverter
import com.example.searchbin.data.network.models.NetworkBinInfoDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object NetworkBinInfoDTOConverter {

    private val gson by lazy { Gson() }

    @TypeConverter
    @JvmStatic
    fun responseToString(networkCommonBinInfoDTO: NetworkBinInfoDTO): String {
        return gson.toJson(networkCommonBinInfoDTO)
    }

    @TypeConverter
    @JvmStatic
    fun stringToResponse(data: String): NetworkBinInfoDTO {
        val listType = object : TypeToken<NetworkBinInfoDTO>() {}.type
        return gson.fromJson(data, listType)
    }
}