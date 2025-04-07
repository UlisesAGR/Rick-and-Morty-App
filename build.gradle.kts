/*
 * build.gradle.kts - Project app
 * Modified by Ulises Gonzalez
 * Copyright (c) 2025. All rights reserved
 */
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.com.google.hilt) apply false
    alias(libs.plugins.com.google.ksp) apply false
}
