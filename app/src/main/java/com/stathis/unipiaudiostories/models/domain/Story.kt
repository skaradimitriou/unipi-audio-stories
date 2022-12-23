package com.stathis.unipiaudiostories.models.domain

import com.stathis.unipiaudiostories.abstraction.UiModel

data class Story(
    val title : String,
    val image : String,
    val text : String,
    val author : String,
    val year : Int
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when(obj) {
        is Story -> title == obj.title && image == obj.image && text == obj.text && author == obj.author && year == obj.year
        else -> false
    }
}
