package com.example.searchbin.utils

import android.os.Looper
import android.widget.TextView
import androidx.annotation.CheckResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.searchbin.data.ErrorState
import com.example.searchbin.data.ResponseStates
import com.example.searchbin.data.models.BinItem
import com.example.searchbin.presentation.utils.CommonUiStates
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

fun <NetworkResponse> buildRequestFlow(
    block: suspend () -> ResponseStates<NetworkResponse>
): Flow<CommonUiStates> {
    return flow {
        emit(CommonUiStates.Loading)
        val result = when (val requestState = block()) {
            is ResponseStates.Success -> CommonUiStates.Success(requestState.data as List<BinItem>)
            is ErrorState.SuccessNoResult -> CommonUiStates.SuccessNoResult
            else -> CommonUiStates.Error(requestState as ErrorState)
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


/**
 * FlowBinding (replica of RxBinding)
 * */

private fun checkMainThread() {
    check(Looper.myLooper() == Looper.getMainLooper()) {
        "Expected to be called on the main thread but was " + Thread.currentThread().name
    }
}

@ExperimentalCoroutinesApi
@CheckResult
fun TextView.textChanges(): Flow<CharSequence?> = callbackFlow {
    checkMainThread()
    val listener = doOnTextChanged { text, _, _, _ -> trySend(text) }

    addTextChangedListener(listener)
    awaitClose { removeTextChangedListener(listener) }
}.onStart { emit(text) }
