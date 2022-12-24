package com.stathis.unipiaudiostories.models.domain

import android.os.Parcelable
import androidx.room.Entity
import com.stathis.unipiaudiostories.abstraction.UiModel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "stories")
data class Story(

    val title : String,
    val image : String,
    val text : String,
    val author : String,
    val year : Int
) : UiModel, Parcelable {
    override fun equalsContent(obj: UiModel): Boolean = when(obj) {
        is Story -> title == obj.title && image == obj.image && text == obj.text && author == obj.author && year == obj.year
        else -> false
    }
}
