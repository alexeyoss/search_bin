package com.example.searchbin.data.network

import android.os.Parcelable
import com.example.searchbin.data.BinItem
import com.example.searchbin.data.models.BankDTO
import com.example.searchbin.data.models.CountryDTO
import com.example.searchbin.data.models.NumberDTO
import com.example.searchbin.data.models.OtherInfoDTO
import kotlinx.parcelize.Parcelize

@Parcelize
data class NetworkCommonBinInfoDTO(
    val number: NumberDTO?,
    val scheme: String? = "",
    val type: String? = "",
    val brand: String? = "",
    val prepaid: Boolean?,
    val country: CountryDTO?,
    val bank: BankDTO?
) : Parcelable {

    companion object {

        fun NetworkCommonBinInfoDTO.extractItems(): List<BinItem> {
            val listOfBinItems = mutableListOf<BinItem>()

            number?.let { listOfBinItems.add(it) }
            country?.let { listOfBinItems.add(it) }
            bank?.let { listOfBinItems.add(it) }
            listOfBinItems.add(this.toOtherInfoDTO())

            return listOfBinItems
        }

        private fun NetworkCommonBinInfoDTO.toOtherInfoDTO(): OtherInfoDTO {
            return OtherInfoDTO(
                scheme = this.scheme,
                type = this.type,
                brand = this.brand,
                prepaid = this.prepaid
            )
        }

    }
}

