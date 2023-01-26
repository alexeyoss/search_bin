package com.example.searchbin.data.mappers

import com.example.searchbin.data.entities.CachedBinInfoDTO
import com.example.searchbin.data.models.NetworkBinInfoDTO

class CachedBinInfoMapper : CachedEntityMapper<CachedBinInfoDTO, NetworkBinInfoDTO> {

    override fun mapToDomainModel(
        foreignModel: NetworkBinInfoDTO,
        binNumber: Long
    ): CachedBinInfoDTO {
        return CachedBinInfoDTO(
            bin = binNumber,
            response = foreignModel,
            createdAt = null
        )
    }

    override fun mapToForeignModel(domainModel: CachedBinInfoDTO): NetworkBinInfoDTO {
        return domainModel.response
    }
}