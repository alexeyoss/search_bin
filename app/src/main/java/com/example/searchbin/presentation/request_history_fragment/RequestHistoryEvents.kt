package com.example.searchbin.presentation.request_history_fragment

sealed interface RequestHistoryEvents {
    object GetRequestHistory : RequestHistoryEvents
}
