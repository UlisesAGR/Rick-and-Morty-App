/*
 * DaoModule.kt
 * Created by Ulises Gonzalez on 20/02/25
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.di.local

import com.rickandmorty.mobile.data.local.database.AppDatabase
import com.rickandmorty.mobile.data.local.database.dao.CharacterDao
import com.rickandmorty.mobile.data.local.database.dao.CharacterRemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideCharacterDao(database: AppDatabase): CharacterDao =
        database.characterDao()

    @Provides
    @Singleton
    fun provideCharacterRemoteKeysDao(database: AppDatabase): CharacterRemoteKeysDao =
        database.characterRemoteKeysDao()
}