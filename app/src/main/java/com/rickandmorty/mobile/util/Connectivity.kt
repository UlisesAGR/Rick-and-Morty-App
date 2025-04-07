/*
 * Connectivity.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.util

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

fun isNetworkAvailable(connectivityManager: ConnectivityManager): Boolean {
    val activeNetwork: Network? = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
    return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
}