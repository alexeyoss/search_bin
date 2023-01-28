package com.example.searchbin.presentation.enter_bin_fragment

sealed interface EnterBinFragmentEvents {
    data class GetBinInfo(val binNumber: String) : EnterBinFragmentEvents
}

