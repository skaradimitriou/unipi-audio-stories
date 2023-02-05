package com.stathis.unipiaudiostories.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.stathis.unipiaudiostories.R
import java.io.ByteArrayOutputStream

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

/**
 * Helper fun to get a string in a non html format.
 */
fun String.toNonHtmlText(): String {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
}

fun ImageView.loadUserImage(imageUrl: String) {
    Glide.with(context).load(imageUrl)
        .placeholder(R.drawable.empty_profile_photo)
        .error(R.drawable.empty_profile_photo)
        .into(this)
}

fun Bitmap.compressBitmap(): ByteArray {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    return baos.toByteArray()
}