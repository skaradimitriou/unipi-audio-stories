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
        Glide.with(this).load(url)
            .placeholder(R.mipmap.audio_book_logo)
            .error(R.mipmap.audio_book_logo)
            .into(this)
    } catch (e: Exception) {
        setImageResource(R.mipmap.audio_book_logo)
    }
}

@BindingAdapter("author", "year")
fun TextView.setStoryAdditionals(author: String?, year: Int?) {
    text = resources.getString(R.string.story_author_year, author, year.toString())
}

@BindingAdapter("setStoryButton")
fun ImageView.setStoryButton(isPlaying: Boolean) {
    if (isPlaying) {
        setImageResource(R.drawable.ic_stop)
    } else {
        setImageResource(R.drawable.ic_play)
    }
}

@BindingAdapter("setStatisticNumber")
fun TextView.setStatisticNumber(counter: Long) {
    text = counter.toString()
}