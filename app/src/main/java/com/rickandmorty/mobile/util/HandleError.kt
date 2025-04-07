/*
 * HandleError.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.util

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
        is UnknownHostException, is ConnectException, is SocketException -> getString(R.string.check_your_internet_connection)
        is SocketTimeoutException, is TimeoutException -> getString(R.string.timeout)
        else -> getString(R.string.please_try_again_later)
    }
}
