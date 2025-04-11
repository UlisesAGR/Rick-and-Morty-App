/*
 * ServiceModule.kt
 * Created by Ulises Gonzalez on 26/02/25
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.di.remote

import com.rickandmorty.mobile.data.remote.service.CharacterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideCharacterService(retrofit: Retrofit): CharacterService =
        retrofit.create(CharacterService::class.java)
}
