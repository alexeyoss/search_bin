package com.example.searchbin.data.network.models

import android.os.Parcelable
import com.example.searchbin.presentation.adapters.BinItemFingerprint
import kotlinx.parcelize.Parcelize

@Parcelize
data class NetworkBinInfoDTO(
    val number: NumberDTO?,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val country: CountryDTO?,
    val bank: BankDTO?
) : Parcelable {

    companion object {

        /** Split nested response to simple domain models [BinItem] corresponding to view types [BinItemFingerprint] */
        fun NetworkBinInfoDTO.extractListOfBinItems(): List<BinItem> {
            val listOfBinItems = mutableListOf<BinItem>()

            listOf(number, country, bank).forEach { binItem ->
                binItem?.let { listOfBinItems.add(it) }
            }
            listOfBinItems.add(this.toOtherInfoDTO())

            return listOfBinItems
        }

        private fun NetworkBinInfoDTO.toOtherInfoDTO(): OtherInfoDTO {
            return OtherInfoDTO(
                scheme = this.scheme, type = this.type, brand = this.brand, prepaid = this.prepaid
            )
        }

    }
}

