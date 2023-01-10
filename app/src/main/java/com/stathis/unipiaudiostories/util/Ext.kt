package com.stathis.unipiaudiostories.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.res.ResourcesCompat
import com.stathis.unipiaudiostories.R

/**
 * This file contains the extension functions that are used across the app
 */

/**
 * Mapper ext functions to return either the value if its not null or a default value.
 */
fun String?.toNotNull(): String = this ?: ""
fun Int?.toNotNull(): Int = this ?: 0
fun Long?.toNotNull(): Long = this ?: 0L

/**
 * Helper method to get a drawable from resources.
 */

fun Context.getAppDrawable(drawable: Int): Drawable? {
    return ResourcesCompat.getDrawable(resources, drawable, null)
}

/**
 * Helper method to get the appropriate favorite icon drawable id (either white or red
 */

fun getAppropriateIcon(isFavorite: Boolean): Int {
    return if (isFavorite) {
        R.drawable.ic_favorite_red
    } else {
        R.drawable.ic_favorite_white
    }
}

/**
 * Helper fun to get a menu item in a safe way
 */

fun Menu.getItemOrNull(position: Int): MenuItem? {
    return try {
        getItem(position)
    } catch (e: Exception) {
        null
    }
}