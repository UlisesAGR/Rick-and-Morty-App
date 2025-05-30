/*
 * HandleError.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.util.exception

import android.content.Context
import com.rickandmorty.mobile.R
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

fun Context.handleError(exception: Throwable?): String {
    log(message = exception.toString())
    return when (exception) {
        is GenericException.CharacterNotFoundException -> getString(R.string.character_not_found)
        is SocketTimeoutException, is TimeoutException -> getString(R.string.timeout)
        is UnknownHostException, is ConnectException, is SocketException -> getString(R.string.check_your_internet_connection)
        else -> getString(R.string.please_try_again_later)
    }
}
