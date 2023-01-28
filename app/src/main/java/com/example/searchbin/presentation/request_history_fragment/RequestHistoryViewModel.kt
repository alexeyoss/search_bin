package com.example.searchbin.presentation.request_history_fragment

import androidx.lifecycle.LiveData
import com.example.searchbin.presentation.utils.CommonSideEffects
import com.example.searchbin.presentation.utils.CommonUiStates
import kotlinx.coroutines.flow.StateFlow

interface RequestHistoryViewModel {
    val uiStateFlow: StateFlow<CommonUiStates>
    val sideEffectsFlow: StateFlow<CommonSideEffects>

    val lastState: LiveData<CommonUiStates>

    fun setEvent(event: RequestHistoryEvents)
}