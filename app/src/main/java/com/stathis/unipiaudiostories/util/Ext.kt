package com.stathis.unipiaudiostories.util

/**
 * This file contains the extension functions that are used across the app
 */

/**
 * Mapper ext functions to return either the value if its not null or a default value.
 */
fun String?.toNotNull(): String = this ?: ""
fun Int?.toNotNull(): Int = this ?: 0