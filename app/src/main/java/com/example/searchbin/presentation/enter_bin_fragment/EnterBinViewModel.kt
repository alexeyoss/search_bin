package com.example.searchbin.presentation.enter_bin_fragment

import com.example.searchbin.presentation.utils.CommonStates
import kotlinx.coroutines.flow.StateFlow

interface EnterBinViewModel {

    val binInfoFlow: StateFlow<CommonStates>

    fun getBinItems(binNumber: String)
}
