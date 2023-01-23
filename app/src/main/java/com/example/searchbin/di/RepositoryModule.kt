package com.example.searchbin.di

import com.example.searchbin.data.MainRepositoryImpl
import com.example.searchbin.domain.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindMainRepository(impl: MainRepositoryImpl): MainRepository

}