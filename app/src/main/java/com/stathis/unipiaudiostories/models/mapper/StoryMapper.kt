package com.stathis.unipiaudiostories.models.mapper

import com.stathis.unipiaudiostories.models.data.StoryDto
import com.stathis.unipiaudiostories.models.domain.Story
import com.stathis.unipiaudiostories.util.toNotNull

object StoryMapper : Mapper<List<StoryDto?>?, List<Story>> {

    override fun fromDataToDomainModel(dataModel: List<StoryDto?>?): List<Story> {
        return dataModel?.map { it.toDomainModel() } ?: listOf()
    }

    override fun fromDomainToDataModel(domainModel: List<Story>): List<StoryDto?>? {
        return domainModel.map { it.toDataModel() }
    }

    private fun StoryDto?.toDomainModel(): Story = Story(
        title = this?.title.toNotNull(),
        image = this?.image.toNotNull(),
        text = this?.text.toNotNull(),
        author = this?.author.toNotNull(),
        year = this?.year.toNotNull()
    )

    private fun Story.toDataModel(): StoryDto = StoryDto(title, image, text, author, year)
}