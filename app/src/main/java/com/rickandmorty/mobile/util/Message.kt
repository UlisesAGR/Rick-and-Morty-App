/*
 * Message.kt
 * Created by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
package com.rickandmorty.mobile.util

import android.content.Context
import android.widget.Toast

fun Context.showToast(text: String?) {
    if (!text.isNullOrBlank()) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
