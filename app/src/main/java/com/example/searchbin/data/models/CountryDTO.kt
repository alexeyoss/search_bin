package com.example.searchbin.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryDTO(
    val numeric: String? = "",
    val alpha2: String? = "",
    val name: String? = "",
    val emoji: String? = "",
    val currency: String? = "",
    val latitude: Int?,
    val longitude: Int?
) : Parcelable, BinItem {
    override fun toList(): List<BinItem> = listOf(this)
}
