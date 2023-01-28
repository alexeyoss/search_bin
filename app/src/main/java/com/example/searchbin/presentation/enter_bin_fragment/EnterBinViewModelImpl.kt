package com.example.searchbin.presentation.enter_bin_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchbin.di.CoroutinesModule
import com.example.searchbin.domain.GetBinItemsUseCase
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
class EnterBinViewModelImpl
@Inject constructor(
    @CoroutinesModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getBinInfoUseCase: GetBinItemsUseCase,
) : ViewModel(), EnterBinViewModel {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.i(throwable)
    }

    private val _sideEffectsFlow = MutableStateFlow<CommonSideEffects>(CommonSideEffects.Initial)
    override val sideEffectsFlow = _sideEffectsFlow.asStateFlow()

    private val _uiStateFlow = MutableStateFlow<CommonUiStates>(CommonUiStates.Initial)
    override val uiStateFlow = _uiStateFlow.asStateFlow()


    override fun setEvent(event: EnterBinFragmentEvents) {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            when (event) {
                is EnterBinFragmentEvents.GetBinInfo -> getBinItems(event.binNumber)
            }
        }
    }

    private fun getBinItems(binNumber: String) {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            getBinInfoUseCase.invoke(binNumber.toLong()).collect { uiState ->
                when (uiState) {
                    is CommonUiStates.Success -> {
                        _sideEffectsFlow.emit(CommonSideEffects.ShowResult)
                        _uiStateFlow.emit(uiState)
                    }
                    is CommonUiStates.Loading -> {
                        _sideEffectsFlow.emit(CommonSideEffects.Loading)
                        _uiStateFlow.emit(CommonUiStates.Loading)
                    }
                    is CommonUiStates.SuccessNoResult -> _sideEffectsFlow.emit(CommonSideEffects.NoSearchResult)
                    is CommonUiStates.Error -> _sideEffectsFlow.emit(CommonSideEffects.ShowError)
                    is CommonUiStates.Initial -> Unit
                }
            }
        }
    }

}