package com.example.searchbin.data.mappers

interface CachedEntityMapper<DomainModel, ForeignModel> {

    fun mapToDomainModel(foreignModel: ForeignModel, binNumber: Long): DomainModel

    fun mapToForeignModel(domainModel: DomainModel): ForeignModel
}