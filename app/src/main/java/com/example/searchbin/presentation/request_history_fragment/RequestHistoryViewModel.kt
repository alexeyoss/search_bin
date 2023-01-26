package com.example.searchbin.presentation.request_history_fragment

import com.example.searchbin.presentation.utils.CommonSideEffects
import com.example.searchbin.presentation.utils.CommonUiStates
import kotlinx.coroutines.flow.StateFlow

interface RequestHistoryViewModel {
    val uiStateFlow: StateFlow<CommonUiStates>
    val sideEffectsFlow: StateFlow<CommonSideEffects>

    fun setEvent(event: RequestHistoryEvents)
}