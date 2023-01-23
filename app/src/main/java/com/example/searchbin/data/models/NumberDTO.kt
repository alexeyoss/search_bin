package com.example.searchbin.data.models

import android.os.Parcelable
import com.example.searchbin.data.BinItem
import kotlinx.parcelize.Parcelize


@Parcelize
data class NumberDTO(
    val length: Int?,
    val luhn: Boolean?
) : Parcelable, BinItem {
    override fun toList(): List<BinItem> = listOf(this)
}
