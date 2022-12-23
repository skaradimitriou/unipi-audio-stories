package com.stathis.unipiaudiostories.callbacks

import com.stathis.unipiaudiostories.models.domain.Story

fun interface StoryCallback {
    fun onStoryTap(story : Story)
}