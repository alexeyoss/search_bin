package com.example.searchbin.di

import android.content.Context
import com.example.searchbin.SearchBinApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApplication(@ApplicationContext application: Context): SearchBinApp =
        application as SearchBinApp

}