/*
 * CharacterNetworkSourceModule.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.di.network

import com.rickandmorty.mobile.data.network.source.CharacterNetworkSource
import com.rickandmorty.mobile.data.network.source.CharacterNetworkSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CharacterNetworkSourceModule {

    @Binds
    @Singleton
    abstract fun provideCharacterNetworkSource(characterNetworkSourceImpl: CharacterNetworkSourceImpl): CharacterNetworkSource
}
