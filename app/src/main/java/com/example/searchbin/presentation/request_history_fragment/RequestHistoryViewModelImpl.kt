package com.example.searchbin.presentation.request_history_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchbin.di.CoroutinesModule
import com.example.searchbin.domain.GetRequestHistoryUseCase
import com.example.searchbin.presentation.utils.CommonSideEffects
import com.example.searchbin.presentation.utils.CommonUiStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RequestHistoryViewModelImpl
@Inject constructor(
    @CoroutinesModule.IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val getRequestHistoryUseCase: GetRequestHistoryUseCase
) : ViewModel(), RequestHistoryViewModel {


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.i(throwable)
    }

    private val _sideEffectsFlow =
        MutableStateFlow<CommonSideEffects>(CommonSideEffects.Initial)
    override val sideEffectsFlow = _sideEffectsFlow.asStateFlow()

    private val _uiStateFlow = MutableStateFlow<CommonUiStates>(CommonUiStates.Initial)
    override val uiStateFlow = _uiStateFlow.asStateFlow()

    private val _lastState = MutableLiveData<CommonUiStates>()
    override val lastState: LiveData<CommonUiStates> = _lastState


    override fun setEvent(event: RequestHistoryEvents) {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            when (event) {
                is RequestHistoryEvents.GetRequestHistory -> getRequestHistory()
            }
        }
    }


    private fun getRequestHistory() {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            getRequestHistoryUseCase.invoke().collect { uiState ->
                when (uiState) {
                    is CommonUiStates.Error -> _sideEffectsFlow.emit(CommonSideEffects.ShowError)
                    is CommonUiStates.Initial -> Unit
                    is CommonUiStates.Loading -> {
                        _sideEffectsFlow.emit(CommonSideEffects.Loading)
                        _uiStateFlow.emit(CommonUiStates.Loading)
                    }
                    is CommonUiStates.Success -> {
                        _sideEffectsFlow.emit(CommonSideEffects.ShowResult)
                        _uiStateFlow.emit(uiState)
                    }
                    is CommonUiStates.SuccessNoResult -> _sideEffectsFlow.emit(CommonSideEffects.NoSearchResult)
                }
            }
        }
    }
}