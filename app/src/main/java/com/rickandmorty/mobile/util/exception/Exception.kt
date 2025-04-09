/*
 * Exception.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.util.exception

sealed class Exception(message: String? = "") : Throwable(message) {
    class CharacterNotFoundException(message: String? = "") : Exception(message)
}
