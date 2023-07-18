package com.example.searchbin.data.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryDTO(
    val numeric: String?,
    val alpha2: String?,
    val name: String?,
    val emoji: String?,
    val currency: String?,
    val latitude: Double?,
    val longitude: Double?
) : Parcelable, BinItem {
    override fun toList(): List<BinItem> = listOf(this)
}
