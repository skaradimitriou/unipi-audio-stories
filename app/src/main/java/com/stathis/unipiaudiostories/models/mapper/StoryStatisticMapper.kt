package com.stathis.unipiaudiostories.models.mapper

import com.stathis.unipiaudiostories.models.data.StoryStatisticDto
import com.stathis.unipiaudiostories.models.domain.StoryStatistic
import com.stathis.unipiaudiostories.util.toNotNull

object StoryStatisticMapper : Mapper<List<StoryStatisticDto?>?, List<StoryStatistic>> {

    override fun fromDataToDomainModel(dataModel: List<StoryStatisticDto?>?): List<StoryStatistic> {
        return dataModel?.map { it.toDomainModel() } ?: listOf()
    }

    override fun fromDomainToDataModel(domainModel: List<StoryStatistic>): List<StoryStatisticDto>? {
        return domainModel.map { StoryStatisticDto(it.storyName, it.counter) }
    }

    private fun StoryStatisticDto?.toDomainModel(): StoryStatistic = StoryStatistic(
        storyName = this?.storyName.toNotNull(),
        counter = this?.counter.toNotNull()
    )
}