package com.example.searchbin.presentation.utils

sealed interface CommonStates {
    data class Success(val data: Any) : CommonStates
    data class Error(val error: Any) : CommonStates
    object Loading : CommonStates
    object Empty : CommonStates
}
