package com.example.searchbin.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OtherInfoDTO(
    val scheme: String? = "",
    val type: String? = "",
    val brand: String? = "",
    val prepaid: Boolean?
) : Parcelable, BinItem {
    override fun toList(): List<BinItem> = listOf(this)
}
