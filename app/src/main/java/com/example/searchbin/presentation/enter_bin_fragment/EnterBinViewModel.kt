package com.example.searchbin.presentation.enter_bin_fragment

import com.example.searchbin.presentation.utils.CommonSideEffects
import com.example.searchbin.presentation.utils.CommonUiStates
import kotlinx.coroutines.flow.StateFlow

interface EnterBinViewModel {

    val uiStateFlow: StateFlow<CommonUiStates>
    val sideEffectsFlow: StateFlow<CommonSideEffects>

    fun setEvent(event: EnterBinFragmentEvents)
}
