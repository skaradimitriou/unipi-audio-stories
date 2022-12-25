package com.stathis.unipiaudiostories.models.repo

import com.stathis.unipiaudiostories.models.domain.Story
import com.stathis.unipiaudiostories.models.domain.StoryStatistic
import kotlinx.coroutines.flow.Flow

interface StoryRepository {

    /**
     * Gets all stories that exist in Realtime Database
     * and emits the value in Kotlin Flow
     */
    suspend fun getAllStories() : Flow<List<Story>>

    suspend fun getStoryStatistics() : Flow<List<StoryStatistic>>
}