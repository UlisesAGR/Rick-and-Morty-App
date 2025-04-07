/*
 * CharacterRepositoryModule.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.di.repository

import com.rickandmorty.mobile.data.repository.CharacterRepositoryImpl
import com.rickandmorty.mobile.domain.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CharacterRepositoryModule {

    @Binds
    @Singleton
    abstract fun provideCharacterRepositoryImpl(characterRepositoryImpl: CharacterRepositoryImpl): CharacterRepository
}
