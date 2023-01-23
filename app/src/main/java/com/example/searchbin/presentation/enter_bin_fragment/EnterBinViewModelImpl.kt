package com.example.searchbin.presentation.enter_bin_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchbin.di.CoroutinesModule
import com.example.searchbin.domain.GetBinItemsUseCase
import com.example.searchbin.presentation.utils.CommonStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EnterBinViewModelImpl
@Inject constructor(
    @CoroutinesModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getBinInfoUseCase: GetBinItemsUseCase
) : ViewModel(), EnterBinViewModel {


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.i(throwable)
    }

    private val _binInfoFlow = MutableStateFlow<CommonStates>(CommonStates.Empty)
    override val binInfoFlow = _binInfoFlow.asStateFlow()


    override fun getBinItems(binNumber: String) {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            getBinInfoUseCase.invoke(binNumber)
                .onEach { commonState -> _binInfoFlow.emit(commonState) }
                .launchIn(viewModelScope)
        }
    }

}