/*
 * RemoteSourceModule.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.di.remote

import com.rickandmorty.mobile.data.remote.source.CharacterRemoteSource
import com.rickandmorty.mobile.data.remote.source.CharacterRemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteSourceModule {

    @Binds
    @Singleton
    abstract fun provideCharacterRemoteSource(characterRemoteSourceImpl: CharacterRemoteSourceImpl): CharacterRemoteSource
}
