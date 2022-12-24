package com.stathis.unipiaudiostories.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stathis.unipiaudiostories.models.domain.Story
import kotlinx.coroutines.flow.Flow

@Dao
interface StoriesDao {

    @Query("SELECT * FROM stories")
    fun getAllCountries(): Flow<List<Story>>

    @Insert
    suspend fun insertStories(list: List<Story>)

    @Query("DELETE FROM stories")
    suspend fun deleteAll()
}