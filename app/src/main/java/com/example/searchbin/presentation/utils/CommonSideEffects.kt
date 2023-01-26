package com.example.searchbin.presentation.utils

sealed interface CommonSideEffects {
    object Loading : CommonSideEffects
    object NoSearchResult : CommonSideEffects
    object ShowError : CommonSideEffects
    object ShowResult : CommonSideEffects
    object Initial : CommonSideEffects
}