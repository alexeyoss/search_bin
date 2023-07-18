package com.example.searchbin.data.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class NumberDTO(
    val length: Int?,
    val luhn: Boolean?
) : Parcelable, BinItem {
    override fun toList(): List<BinItem> = listOf(this)
}
