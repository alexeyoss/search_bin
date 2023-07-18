package com.example.searchbin.presentation.utils

import com.example.searchbin.data.network.ErrorState
import com.example.searchbin.data.network.models.BinItem

sealed interface CommonUiStates {
    data class Success(val data: List<BinItem>) : CommonUiStates
    object SuccessNoResult : CommonUiStates
    data class Error(val error: ErrorState) : CommonUiStates
    object Loading : CommonUiStates
    object Initial : CommonUiStates
}
