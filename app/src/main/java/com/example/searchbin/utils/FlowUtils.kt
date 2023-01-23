package com.example.searchbin.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.searchbin.presentation.utils.CommonStates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

fun <NetworkResponse> buildRequestFlow(
    block: suspend () -> NetworkResponseStates<NetworkResponse>
): Flow<CommonStates> {
    return flow {
        emit(CommonStates.Loading)
        val result = when (val requestState = block()) {
            is NetworkResponseStates.Success -> {
                CommonStates.Success(requestState.data as Any)
            }
            else -> CommonStates.Error(requestState)
        }
        emit(result)
    }
}


fun <T> Flow<T>.collectOnLifecycle(
    lifecycleOwner: LifecycleOwner,
    state: Lifecycle.State,
    collector: (T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(state) {
            collect {
                collector(it)
            }
        }
    }
}

fun <T> Flow<T>.collectOnLifecycle(
    lifecycleOwner: AppCompatActivity,
    collector: (T) -> Unit
) {
    collectOnLifecycle(lifecycleOwner, Lifecycle.State.STARTED, collector)
}

fun <T> Flow<T>.collectOnLifecycle(
    lifecycleOwner: Fragment,
    collector: (T) -> Unit
) {
    collectOnLifecycle(lifecycleOwner, Lifecycle.State.RESUMED, collector)
}