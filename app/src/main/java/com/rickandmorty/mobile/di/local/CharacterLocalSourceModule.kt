/*
 * CharacterLocalSourceModule.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.di.local

import com.rickandmorty.mobile.data.local.source.CharacterLocalSource
import com.rickandmorty.mobile.data.local.source.CharacterLocalSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CharacterLocalSourceModule {

    @Binds
    @Singleton
    abstract fun provideCharacterLocalSource(characterLocalSourceImpl: CharacterLocalSourceImpl): CharacterLocalSource
}
