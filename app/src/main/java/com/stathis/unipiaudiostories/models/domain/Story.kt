package com.stathis.unipiaudiostories.models.domain

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stathis.unipiaudiostories.abstraction.UiModel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "stories")
data class Story(

    @NonNull
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "text")
    val text: String,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "year")
    val year: Int

) : UiModel, Parcelable {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is Story -> title == obj.title && image == obj.image && text == obj.text && author == obj.author && year == obj.year
        else -> false
    }
}
