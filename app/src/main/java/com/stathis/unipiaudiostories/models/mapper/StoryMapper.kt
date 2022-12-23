package com.stathis.unipiaudiostories.models.mapper

import com.stathis.unipiaudiostories.models.data.StoryDto
import com.stathis.unipiaudiostories.models.domain.Story
import com.stathis.unipiaudiostories.util.toNotNull

object StoryMapper : Mapper<StoryDto?, Story> {

    override fun fromDataToDomainModel(dataModel: StoryDto?): Story {
        return dataModel.toDomainModel()
    }

    override fun fromDomainToDataModel(domainModel: Story): StoryDto? {
        return domainModel.toDataModel()
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