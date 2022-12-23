package com.stathis.unipiaudiostories.callbacks

import com.stathis.unipiaudiostories.models.Story

fun interface StoryCallback {
    fun onStoryTap(story : Story)
}