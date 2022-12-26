package com.stathis.unipiaudiostories.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.stathis.unipiaudiostories.models.domain.Story
import kotlinx.coroutines.flow.Flow

/**
 * [Dao] is the main class where the database interactions are defined.
 * They can include a variety of query methods.
 */

@Dao
interface StoriesDao {

    /**
     * Query that returns all the stories sorted by title ASC
     */

    @Query("SELECT * FROM stories ORDER BY title ASC")
    fun getAllCountries(): Flow<List<Story>>

    /**
     * Query that inserts a list of Story model
     */

    @Insert
    suspend fun insertStories(list: List<Story>)

    /**
     * Query that deletes all the stories from the local db.
     */

    @Query("DELETE FROM stories")
    suspend fun deleteAll()
}