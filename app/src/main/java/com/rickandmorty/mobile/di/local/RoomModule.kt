/*
 * RoomModule.kt
 * Created by Ulises Gonzalez on 20/02/25
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.di.local

import android.content.Context
import androidx.room.Room
import com.rickandmorty.mobile.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            name = "DATABASE_NAME",
        ).fallbackToDestructiveMigration(false).build()
}
