package com.stathis.unipiaudiostories.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.stathis.unipiaudiostories.R

/**
 * This file contains the binding adapters that are used across the app
 */

@BindingAdapter("imageFromUrl")
fun ImageView.setImageFromUrl(url: String?) {
    try {
        Glide.with(this).load(url).error(R.mipmap.ic_launcher).into(this)
    } catch (e: Exception) {
        setImageResource(R.mipmap.ic_launcher)
    }
}

@BindingAdapter("author", "year")
fun TextView.setStoryAdditionals(author: String?, year: Int?) {
    text = "$author | $year"
}