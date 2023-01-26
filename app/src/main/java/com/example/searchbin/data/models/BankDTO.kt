package com.example.searchbin.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BankDTO(
    val name: String? = "",
    val url: String? = "",
    val phone: String? = "",
    val city: String? = ""
) : Parcelable, BinItem {
    override fun toList(): List<BinItem> = listOf(this)
}
