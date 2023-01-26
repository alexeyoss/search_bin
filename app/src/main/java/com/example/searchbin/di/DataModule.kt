package com.example.searchbin.di

import android.content.Context
import com.example.searchbin.data.CachedBinInfoStore
import com.example.searchbin.data.CachedBinInfoStoreImpl
import com.example.searchbin.data.db.BinDb
import com.example.searchbin.data.mappers.CachedBinInfoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideBinDb(
        @ApplicationContext app: Context,
    ): BinDb = BinDb.getInstance(app)


    @Singleton
    @Provides
    fun provideCachedBinInfoMapper() = CachedBinInfoMapper()

    @Singleton
    @Provides
    fun provideMovieCache(impl: CachedBinInfoStoreImpl): CachedBinInfoStore {
        return impl
    }
}