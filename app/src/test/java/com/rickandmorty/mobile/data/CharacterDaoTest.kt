/*
 * CharacterDaoTest.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.rickandmorty.mobile.data.local.database.AppDatabase
import com.rickandmorty.mobile.data.local.database.dao.CharacterDao
import com.rickandmorty.mobile.util.DispatcherRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
class CharacterDaoTest {

    @Inject
    lateinit var appDatabase: AppDatabase

    @Inject
    lateinit var characterDao: CharacterDao

    @get:Rule
    val dispatcherRule = DispatcherRule()

    @Before
    fun setUp() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java,
        ).allowMainThreadQueries().build()
        characterDao = appDatabase.characterDao()
    }

    @Test
    fun `Get All Characters Test`(): Unit = runTest {
        // Given
        // When
        // Then
    }

    @After
    fun closeAppDatabase() {
        appDatabase.close()
    }
}
