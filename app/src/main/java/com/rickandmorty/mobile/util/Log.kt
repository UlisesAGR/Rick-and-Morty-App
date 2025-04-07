/*
 * Logs.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.util

import android.util.Log
import com.rickandmorty.mobile.util.Constants.ERROR

fun log(message: String) {
    Log.d(ERROR, message)
}
