package com.stathis.unipiaudiostories.models.domain

import com.stathis.unipiaudiostories.abstraction.UiModel

data class StoryStatistic(
    val storyName: String,
    var counter: Long
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is StoryStatistic -> storyName == obj.storyName && counter == obj.counter
        else -> false
    }
}
