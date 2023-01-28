package com.example.searchbin.presentation.enter_bin_fragment

sealed interface EnterBinItemClick {
    data class URL(val data: String) : EnterBinItemClick
    data class PHONE(val data: String) : EnterBinItemClick
    data class LOCATION(val data: String) : EnterBinItemClick
}