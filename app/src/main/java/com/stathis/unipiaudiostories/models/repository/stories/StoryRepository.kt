package com.stathis.unipiaudiostories.models.repository.stories

import com.stathis.unipiaudiostories.models.domain.Story
import com.stathis.unipiaudiostories.models.domain.StoryStatistic
import kotlinx.coroutines.flow.Flow

/**
 * The [StoryRepository] contains all the methods to get Stories and Statistics.
 */

interface StoryRepository {

    /**
     * Gets all stories that exist in Realtime Database
     * and emits the value in Kotlin Flow
     */
    suspend fun getAllStories() : Flow<List<Story>>

    suspend fun getAllFavorites() : Flow<List<Story>>

    suspend fun getStoryStatistics() : Flow<List<StoryStatistic>>
}