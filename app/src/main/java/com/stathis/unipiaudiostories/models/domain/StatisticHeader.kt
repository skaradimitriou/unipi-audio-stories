package com.stathis.unipiaudiostories.models.domain

import com.stathis.unipiaudiostories.abstraction.UiModel

data class StatisticHeader(
    val totalCounter: Long
) : UiModel {
    override fun equalsContent(obj: UiModel): Boolean = when (obj) {
        is StatisticHeader -> totalCounter == obj.totalCounter
        else -> false
    }
}
