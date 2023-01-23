package com.example.searchbin.domain

import com.example.searchbin.presentation.utils.CommonStates
import com.example.searchbin.utils.buildRequestFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBinItemsUseCase
@Inject
constructor(
    private val mainRepository: MainRepository
) {

    suspend operator fun invoke(binNumber: String): Flow<CommonStates> {
        return buildRequestFlow {
            mainRepository.getBinInfo(binNumber)
        }
    }
}