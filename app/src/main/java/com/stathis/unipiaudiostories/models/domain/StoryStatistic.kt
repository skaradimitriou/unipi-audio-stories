package com.stathis.unipiaudiostories.models.domain

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stathis.unipiaudiostories.abstraction.UiModel

@Entity(tableName = "statistics")
data class StoryStatistic(

    @NonNull
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "storyName")
    val storyName: String,

    @ColumnInfo(name = "counter")
    var counter: Long

) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is StoryStatistic -> storyName == obj.storyName && counter == obj.counter
        else -> false
    }
}
