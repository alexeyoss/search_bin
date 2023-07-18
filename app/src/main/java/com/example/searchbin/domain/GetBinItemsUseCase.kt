package com.example.searchbin.domain

import com.example.searchbin.di.CoroutinesModule
import com.example.searchbin.presentation.utils.CommonUiStates
import com.example.searchbin.utils.buildRequestFlow
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

/**
 * Fetch info about BIN number from Internet
 * */
class GetBinItemsUseCase
@Inject constructor(
    private val mainRepository: MainRepository,
    @CoroutinesModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.i(throwable)
        createScope()
    }
    private val scope = createScope()
    private fun createScope(): CoroutineScope = CoroutineScope(Job() + ioDispatcher)

    suspend operator fun invoke(binNumber: Long): Flow<CommonUiStates> {
        return buildRequestFlow {
            withContext(scope.coroutineContext + exceptionHandler) {
                mainRepository.fetchBinData(binNumber)
            }
        }
    }


}